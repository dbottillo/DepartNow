package com.dbottillo.departnow.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TflEntity(
    val id: String,
    val expectedArrival: String,
    val towards: String
)
