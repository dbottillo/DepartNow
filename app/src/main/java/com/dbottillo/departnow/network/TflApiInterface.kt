package com.dbottillo.departnow.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TflApiInterface {

    @Suppress("LongParameterList")
    @GET("StopPoint/{station}/Arrivals")
    suspend fun stationTimetable(
        @Path(value = "station") station: String,
    ): Response<List<TflEntity>>
}
