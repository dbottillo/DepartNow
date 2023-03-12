package com.dbottillo.replacename.feature.departures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbottillo.replacename.ApiResult
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
        firstTrain = DeparturesUiTrainData(
            minutes = 0,
            destination = "-",
            time = "HH:mm"
        ),
        secondTrain = DeparturesUiTrainData(
            minutes = 0,
            destination = "-",
            time = "HH:mm"
        )
    )

    private val _dataFlow = MutableStateFlow(DeparturesData)
    private val _statusFlow = MutableStateFlow<DeparturesUiStatus>(DeparturesUiStatus.Idle)

    val uiState: StateFlow<DeparturesUiState> = combine(_dataFlow, _statusFlow) { data, status ->
        DeparturesUiState(departureData = map(data), status = status)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DeparturesUiState(departureData = initialData, status = DeparturesUiStatus.Idle)
    )

    @Suppress("UNUSED_PARAMETER", "MagicNumber")
    private fun map(data: DeparturesData): DeparturesUiData {
        return DeparturesUiData(
            firstTrain = DeparturesUiTrainData(
                minutes = 15,
                destination = "Moorgate",
                time = "15:16"
            ),
            secondTrain = DeparturesUiTrainData(
                minutes = 30,
                destination = "Moorgate",
                time = "15:41"
            )
        )
    }

    init {
        viewModelScope.launch {
            _statusFlow.emit(DeparturesUiStatus.Loading)
            when (val res = repository.get()) {
                is ApiResult.Success -> {
                    _dataFlow.emit(res.data)
                    _statusFlow.emit(DeparturesUiStatus.Idle)
                }
                is ApiResult.Error -> {
                    _statusFlow.emit(DeparturesUiStatus.Error)
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

    object Error : DeparturesUiStatus()
}

data class DeparturesUiData(
    val firstTrain: DeparturesUiTrainData,
    val secondTrain: DeparturesUiTrainData
)

data class DeparturesUiTrainData(
    val minutes: Int,
    val destination: String,
    val time: String
)
