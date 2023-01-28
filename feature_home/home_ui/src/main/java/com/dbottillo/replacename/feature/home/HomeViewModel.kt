package com.dbottillo.replacename.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<Lce<Todo>>(Lce.Loading)
    val uiState: StateFlow<Lce<Todo>> = _uiStateFlow

    init {
        viewModelScope.launch {
            _uiStateFlow.value = when (val res = repository.get()) {
                is ApiResult.Success -> Lce.Data(res.data)
                is ApiResult.Error -> Lce.Error(res.exception)
            }
        }
    }
}
