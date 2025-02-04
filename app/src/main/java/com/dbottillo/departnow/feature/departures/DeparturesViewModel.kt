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
        firstTrain = DeparturesUi.None,
        secondTrain = DeparturesUi.None,
        otherTrains = emptyList(),
        firstBus = DeparturesUi.None,
        secondBus = DeparturesUi.None,
        otherBus = emptyList()
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
        val mapped = data.departures.all
            .filter { it.best_arrival_estimate_mins != null && it.expected_arrival_time != null }
            .map { mapTrainDeparture(it) }
            .sortedBy { it.minutes }
        val before = mapped.filter { it.minutes <= 9 }
        val after = mapped.filter { it.minutes > 9 }
        val beforeString = before.joinToString(separator = "-") {
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
            firstTrain = after.firstOrNull() ?: DeparturesUi.None,
            secondTrain = after.getOrNull(1) ?: DeparturesUi.None,
            otherTrains = after.drop(2),
            beforeBus = beforeBusString,
            firstBus = afterBus.firstOrNull() ?: DeparturesUi.None,
            secondBus = afterBus.getOrNull(1) ?: DeparturesUi.None,
            otherBus = afterBus.drop(2)
        )
    }

    @Suppress("ReturnCount")
    private fun mapTrainDeparture(departureResponse: DepartureResponse): DeparturesUi.Data {
        val estimateMins = departureResponse.best_arrival_estimate_mins!!
        val expectedArrivalTime = departureResponse.expected_arrival_time!!
        return DeparturesUi.Data(
            minutes = estimateMins,
            destination = departureResponse.station_detail.destination.station_name,
            time = expectedArrivalTime
        )
    }

    @Suppress("ReturnCount")
    private fun mapBusDeparture(entity: TflEntity): DeparturesUi.Data {
        val (minutes, time) = calculateMinutes(entity)
        return DeparturesUi.Data(
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
    val firstTrain: DeparturesUi,
    val secondTrain: DeparturesUi,
    val otherTrains: List<DeparturesUi>,
    val beforeBus: String? = "",
    val firstBus: DeparturesUi,
    val secondBus: DeparturesUi,
    val otherBus: List<DeparturesUi>,
)

sealed class DeparturesUi {
    data object None : DeparturesUi()

    data class Data(
        val minutes: Int,
        val destination: String,
        val time: String
    ) : DeparturesUi()
}
