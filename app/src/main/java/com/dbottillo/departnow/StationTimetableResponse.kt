@file:Suppress("ConstructorParameterNaming")
package com.dbottillo.departnow

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationTimetableResponse(
    val date: String,
    val time_of_day: String,
    val departures: DeparturesResponse
)

@JsonClass(generateAdapter = true)
data class DeparturesResponse(
    val all: List<DepartureResponse>
)

@JsonClass(generateAdapter = true)
data class DepartureResponse(
    val expected_arrival_time: String?,
    val best_arrival_estimate_mins: Int?,
    val station_detail: StationDetailResponse
)

@JsonClass(generateAdapter = true)
data class StationDetailResponse(
    val destination: StationDetailDestinationResponse
)

@JsonClass(generateAdapter = true)
data class StationDetailDestinationResponse(
    val station_name: String
)

@JsonClass(generateAdapter = true)
data class BusStopTimetableResponse(
    val departures: BusStopDepartureResponse
)

@JsonClass(generateAdapter = true)
data class BusStopDepartureResponse(
    val all: List<BusDepartureResponse>
)

@JsonClass(generateAdapter = true)
data class BusDepartureResponse(
    val expected_departure_time: String?,
    val direction: String,
    val expected: BusExpectedResponse?
)

@JsonClass(generateAdapter = true)
data class BusExpectedResponse(
    val arrival: BusExpectedArrivalResponse
)

@JsonClass(generateAdapter = true)
data class BusExpectedArrivalResponse(
    val date: String?,
    val time: String?
)
