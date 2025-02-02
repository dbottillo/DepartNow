package com.dbottillo.departnow.feature.departures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbottillo.departnow.ApiResult
import com.dbottillo.departnow.DepartureResponse
import com.dbottillo.departnow.StationTimetableResponse
import com.dbottillo.departnow.network.TflEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class DeparturesViewModel @Inject constructor(
    private val repository: DeparturesRepository
) : ViewModel() {

    private val initialData = DeparturesUiData(
        firstTrain = DeparturesUiTrain.None,
        secondTrain = DeparturesUiTrain.None,
        thirdTrain = DeparturesUiTrain.None,
        fourthTrain = DeparturesUiTrain.None,
        firstBus = DeparturesUiBus.None,
        secondBus = DeparturesUiBus.None
    )

    private val _dataFlow = MutableStateFlow<StationTimetableResponse?>(null)
    private val _busDataFlow = MutableStateFlow<List<TflEntity>?>(null)
    private val _statusFlow = MutableStateFlow<DeparturesUiStatus>(DeparturesUiStatus.Idle)

    val uiState: StateFlow<DeparturesUiState> = combine(_dataFlow, _busDataFlow, _statusFlow) { data, bus, status ->
        DeparturesUiState(departureData = map(data, bus ?: emptyList()), status = status)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DeparturesUiState(departureData = initialData, status = DeparturesUiStatus.Idle)
    )

    @Suppress("MagicNumber")
    private fun map(data: StationTimetableResponse?, bus: List<TflEntity>): DeparturesUiData {
        if (data == null) return initialData
        val mapped = data.departures.all.map { mapTrainDeparture(it) }
        val before = mapped.filter { it is DeparturesUiTrain.Data && it.minutes <= 9 }
        val after = mapped.filter { it is DeparturesUiTrain.None || (it as DeparturesUiTrain.Data).minutes > 9 }
        val beforeString = before.filterIsInstance<DeparturesUiTrain.Data>().joinToString(separator = "-") {
            "In ${it.minutes} (${it.time})"
        }
        val mappedBus = bus.map { mapBusDeparture(it) }.sortedBy { it.minutes }
        val beforeBus = mappedBus.filter { it.minutes <= 6 }
        val afterBus = mappedBus.filter { it.minutes > 6 }
        val beforeBusString = beforeBus.joinToString(separator = "-") {
            "In ${it.minutes} (${it.time})"
        }
        return DeparturesUiData(
            lastTimeUpdated = data.time_of_day,
            trainBefore = beforeString,
            firstTrain = after.firstOrNull() ?: DeparturesUiTrain.None,
            secondTrain = after.getOrNull(1) ?: DeparturesUiTrain.None,
            thirdTrain = after.getOrNull(2) ?: DeparturesUiTrain.None,
            fourthTrain = after.getOrNull(23) ?: DeparturesUiTrain.None,
            beforeBus = beforeBusString,
            firstBus = afterBus.firstOrNull() ?: DeparturesUiBus.None,
            secondBus = afterBus.getOrNull(1) ?: DeparturesUiBus.None,
        )
    }

    @Suppress("ReturnCount")
    private fun mapTrainDeparture(departureResponse: DepartureResponse): DeparturesUiTrain {
        val estimateMins = departureResponse.best_arrival_estimate_mins ?: return DeparturesUiTrain.None
        val expectedArrivalTime = departureResponse.expected_arrival_time ?: return DeparturesUiTrain.None
        return DeparturesUiTrain.Data(
            minutes = estimateMins,
            destination = departureResponse.station_detail.destination.station_name,
            time = expectedArrivalTime
        )
    }

    @Suppress("ReturnCount")
    private fun mapBusDeparture(entity: TflEntity): DeparturesUiBus.Data {
        val (minutes, time) = calculateMinutes(entity)
        return DeparturesUiBus.Data(
            minutes = minutes.toInt(),
            time = time,
            destination = entity.towards
        )
    }

    @Suppress("ReturnCount")
    private fun calculateMinutes(expected: TflEntity): Pair<Long, String> {
        val arrival = OffsetDateTime.parse(expected.expectedArrival)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val now = OffsetDateTime.now()
        return ChronoUnit.MINUTES.between(now, arrival) to arrival.format(formatter)
    }

    init {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            loadTrainData()
            loadBusData()
        }
    }

    private suspend fun loadTrainData() {
        _statusFlow.emit(DeparturesUiStatus.Loading)
        when (val res = repository.getStationTimetable()) {
            is ApiResult.Success -> {
                _dataFlow.emit(res.data)
                _statusFlow.emit(DeparturesUiStatus.Idle)
            }
            is ApiResult.Error -> {
                Log.e("TAG", "error: ${res.exception}")
                _statusFlow.emit(DeparturesUiStatus.Error(res.exception))
            }
        }
    }

    private suspend fun loadBusData() {
        when (val res = repository.getBusDepartures()) {
            is ApiResult.Success -> {
                _busDataFlow.emit(res.data)
            }
            is ApiResult.Error -> {
                Log.e("TAG", "error: ${res.exception}")
                _statusFlow.emit(DeparturesUiStatus.Error(res.exception))
            }
        }
    }
}

data class DeparturesUiState(
    val departureData: DeparturesUiData,
    val status: DeparturesUiStatus
)

sealed class DeparturesUiStatus {
    object Idle : DeparturesUiStatus()
    object Loading : DeparturesUiStatus()

    data class Error(val throwable: Throwable) : DeparturesUiStatus()
}

data class DeparturesUiData(
    val lastTimeUpdated: String = "-",
    val trainBefore: String = "",
    val firstTrain: DeparturesUiTrain,
    val secondTrain: DeparturesUiTrain,
    val thirdTrain: DeparturesUiTrain,
    val fourthTrain: DeparturesUiTrain,
    val beforeBus: String? = "",
    val firstBus: DeparturesUiBus,
    val secondBus: DeparturesUiBus,
)

sealed class DeparturesUiTrain {
    data object None : DeparturesUiTrain()

    data class Data(
        val minutes: Int,
        val destination: String,
        val time: String
    ) : DeparturesUiTrain()
}

sealed class DeparturesUiBus {
    data object None : DeparturesUiBus()

    data class Data(
        val minutes: Int,
        val time: String,
        val destination: String
    ) : DeparturesUiBus()
}
