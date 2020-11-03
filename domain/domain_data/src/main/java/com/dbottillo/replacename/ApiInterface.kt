package com.dbottillo.replacename

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/todos/{id}")
    suspend fun getTodo(@Path(value = "id") todoId: Int): Response<Todo>
}
