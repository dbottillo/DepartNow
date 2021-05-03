package com.dbottillo.replacename.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val data: LiveData<Lce<Todo>> = liveData(Dispatchers.IO) {
        emit(Lce.Loading)
        when (val res = repository.get()) {
            is ApiResult.Success -> emit(Lce.Data(res.data))
            is ApiResult.Error -> emit(Lce.Error(res.exception))
        }
    }
}
