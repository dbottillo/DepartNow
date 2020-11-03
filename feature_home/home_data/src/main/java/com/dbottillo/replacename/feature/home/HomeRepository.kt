package com.dbottillo.replacename.feature.home

import com.dbottillo.replacename.ApiInterface
import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.Todo
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiInterface
) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun get(): ApiResult<Todo> {
        return try {
            val response = api.getTodo(TODO_ID)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResult.Success(body)
            }
            ApiResult.Error(Throwable("${response.code()} ${response.message()}"))
        } catch (e: Exception) {
            ApiResult.Error(Throwable(e.message ?: e.toString()))
        }
    }
}

private const val TODO_ID = 5
