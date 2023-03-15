package com.dbottillo.replacename

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeparturesApiInterface {

    @Suppress("LongParameterList")
    @GET("train/station_timetables/crs%3A{station}.json?station_detail=origin%2Cdestination")
    suspend fun stationTimetable(
        @Path(value = "station") station: String,
        @Query(value = "app_key") appKey: String,
        @Query(value = "app_id") appId: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "live") live: Boolean,
        @Query(value = "train_status") trainStatus: String,
        @Query(value = "type") type: String,
        @Query(value = "calling_at") callingAt: String
    ): Response<StationTimetableResponse>

    @GET("bus/stop_timetables/{bus_stop}.json")
    suspend fun busStopTimetable(
        @Path(value = "bus_stop") busStop: String,
        @Query(value = "app_key") appKey: String,
        @Query(value = "app_id") appId: String,
        @Query(value = "limit") limit: Int,
        @Query(value = "live") live: Boolean
    ): Response<BusStopTimetableResponse>
}
