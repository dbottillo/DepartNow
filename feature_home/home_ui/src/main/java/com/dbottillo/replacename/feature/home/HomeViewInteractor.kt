package com.dbottillo.replacename.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewInteractor @Inject constructor(
    val repository: HomeRepository
) {

    fun get(): LiveData<Lce<Todo>> {
        return liveData(Dispatchers.IO) {
            emit(Lce.Loading)
            emit(repository.get())
        }
    }
}
