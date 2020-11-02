package com.dbottillo.replacename

sealed class Lce<out T> {
    object Loading : Lce<Nothing>()
    class Data<out T>(val data: T) : Lce<T>()
    class Error(val throwable: Throwable) : Lce<Nothing>()
}
