package com.dbottillo.replacename.feature.departures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.DepartureResponse
import com.dbottillo.replacename.StationTimetableResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeparturesViewModel @Inject constructor(
    private val repository: DeparturesRepository
) : ViewModel() {

    private val initialData = DeparturesUiData(
        firstTrain = DeparturesUiTrain.Data(
            minutes = 0,
            destination = "-",
            time = "HH:mm"
        ),
        secondTrain = DeparturesUiTrain.Data(
            minutes = 0,
            destination = "-",
            time = "HH:mm"
        )
    )

    private val _dataFlow = MutableStateFlow<StationTimetableResponse?>(null)
    private val _statusFlow = MutableStateFlow<DeparturesUiStatus>(DeparturesUiStatus.Idle)

    val uiState: StateFlow<DeparturesUiState> = combine(_dataFlow, _statusFlow) { data, status ->
        DeparturesUiState(departureData = map(data), status = status)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DeparturesUiState(departureData = initialData, status = DeparturesUiStatus.Idle)
    )

    @Suppress("UNUSED_PARAMETER", "MagicNumber")
    private fun map(data: StationTimetableResponse?): DeparturesUiData {
        if (data == null) return initialData
        return DeparturesUiData(
            lastTimeUpdated = data.time_of_day,
            firstTrain = mapTrainDeparture(data.departures.all.firstOrNull()),
            secondTrain = mapTrainDeparture(data.departures.all.getOrNull(1))
        )
    }

    @Suppress("ReturnCount")
    private fun mapTrainDeparture(departureResponse: DepartureResponse?): DeparturesUiTrain {
        return departureResponse?.let {
            val estimateMins = it.best_arrival_estimate_mins ?: return DeparturesUiTrain.None
            val expectedArrivalTime = it.expected_arrival_time ?: return DeparturesUiTrain.None
            DeparturesUiTrain.Data(
                minutes = estimateMins,
                destination = it.station_detail.destination.station_name,
                time = expectedArrivalTime
            )
        } ?: DeparturesUiTrain.None
    }

    init {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            _statusFlow.emit(DeparturesUiStatus.Loading)
            when (val res = repository.get()) {
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
    val secondTrain: DeparturesUiTrain
)

sealed class DeparturesUiTrain {
    object None : DeparturesUiTrain()

    data class Data(
        val minutes: Int,
        val destination: String,
        val time: String
    ) : DeparturesUiTrain()
}
