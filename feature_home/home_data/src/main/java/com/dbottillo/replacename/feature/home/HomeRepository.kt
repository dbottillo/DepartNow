package com.dbottillo.replacename.feature.home

import com.dbottillo.replacename.ApiInterface
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiInterface
) {

    @Suppress("FunctionOnlyReturningConstant")
    suspend fun get(): Lce<Todo> {
        try {
            val response = api.getTodo(TODO_ID)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Lce.Data(body)
            }
            return Lce.Error(Throwable(" ${response.code()} ${response.message()}"))
        } catch (e: Exception) {
            return Lce.Error(Throwable(e.message ?: e.toString()))
        }
    }
}

private const val TODO_ID = 5
