package com.dbottillo.replacename.feature.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo

class HomeViewModel @ViewModelInject constructor(
    private val interactor: HomeViewInteractor
) : ViewModel() {

    val data: LiveData<Lce<Todo>> = interactor.get()
}
