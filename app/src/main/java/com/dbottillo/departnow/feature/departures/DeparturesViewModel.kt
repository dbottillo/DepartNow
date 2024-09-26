package com.dbottillo.departnow.feature.departures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbottillo.departnow.ApiResult
import com.dbottillo.departnow.BusDepartureResponse
import com.dbottillo.departnow.BusExpectedResponse
import com.dbottillo.departnow.BusStopTimetableResponse
import com.dbottillo.departnow.DepartureResponse
import com.dbottillo.departnow.StationTimetableResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
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
        fourthTrain = DeparturesUiTrain.None
    )

    private val _dataFlow = MutableStateFlow<StationTimetableResponse?>(null)
    private val _busDataFlow = MutableStateFlow<BusStopTimetableResponse?>(null)
    private val _statusFlow = MutableStateFlow<DeparturesUiStatus>(DeparturesUiStatus.Idle)

    val uiState: StateFlow<DeparturesUiState> = combine(_dataFlow, _statusFlow) { data, status ->
        DeparturesUiState(departureData = map(data), status = status)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DeparturesUiState(departureData = initialData, status = DeparturesUiStatus.Idle)
    )

    @Suppress("MagicNumber")
    private fun map(data: StationTimetableResponse?): DeparturesUiData {
        if (data == null) return initialData
        return DeparturesUiData(
            lastTimeUpdated = data.time_of_day,
            firstTrain = mapTrainDeparture(data.departures.all.firstOrNull()),
            secondTrain = mapTrainDeparture(data.departures.all.getOrNull(1)),
            thirdTrain = mapTrainDeparture(data.departures.all.getOrNull(2)),
            fourthTrain = mapTrainDeparture(data.departures.all.getOrNull(3))
        )
    }

    @Suppress("ReturnCount")
    private fun mapTrainDeparture(departureResponse: DepartureResponse?): DeparturesUiTrain {
        return departureResponse?.let {
            val estimateMins = it.best_arrival_estimate_mins ?: return DeparturesUiTrain.None
            val expectedArrivalTime = it.expected_arrival_time ?: return DeparturesUiTrain.None
            DeparturesUiTrain.Data(
                minutes = estimateMins.toString(),
                destination = it.station_detail.destination.station_name,
                time = expectedArrivalTime
            )
        } ?: DeparturesUiTrain.None
    }

    @Suppress("ReturnCount")
    private fun mapBusDeparture(departureResponse: BusDepartureResponse?): DeparturesUiBus {
        return departureResponse?.let {
            val time = it.expected_departure_time ?: return DeparturesUiBus.None
            DeparturesUiBus.Data(
                minutes = calculateMinutes(it.expected),
                time = time,
                destination = it.direction
            )
        } ?: DeparturesUiBus.None
    }

    @Suppress("ReturnCount")
    private fun calculateMinutes(expected: BusExpectedResponse?): String {
        if (expected == null) return "-"
        val date = expected.arrival.date
        val time = expected.arrival.time
        if (date == null || time == null) return "-"
        val arrival = OffsetDateTime.parse("${date}T$time:00+00:00")
        val now = OffsetDateTime.now()
        return ChronoUnit.MINUTES.between(now, arrival).toString()
    }

    init {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            loadTrainData()
            // loadBusData()
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
    val firstTrain: DeparturesUiTrain,
    val secondTrain: DeparturesUiTrain,
    val thirdTrain: DeparturesUiTrain,
    val fourthTrain: DeparturesUiTrain
)

sealed class DeparturesUiTrain {
    object None : DeparturesUiTrain()

    data class Data(
        val minutes: String,
        val destination: String,
        val time: String
    ) : DeparturesUiTrain()
}

sealed class DeparturesUiBus {
    object None : DeparturesUiBus()

    data class Data(
        val minutes: String,
        val time: String,
        val destination: String
    ) : DeparturesUiBus()
}
