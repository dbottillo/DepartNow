package com.dbottillo.replacename.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dbottillo.replacename.Lce
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewInteractor @Inject constructor(
    val repository: HomeRepository
) {

    fun get(): LiveData<Lce<String>> {
        return liveData(Dispatchers.IO) {
            emit(Lce.Loading)
            emit(Lce.Data(repository.get()))
        }
    }
}
