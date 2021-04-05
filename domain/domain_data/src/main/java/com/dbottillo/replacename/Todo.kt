package com.dbottillo.replacename

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int = 0,
    val title: String = "",
    val completed: Boolean = false
)
