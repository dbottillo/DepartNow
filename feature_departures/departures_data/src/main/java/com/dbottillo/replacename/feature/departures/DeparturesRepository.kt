package com.dbottillo.replacename.feature.departures

import com.dbottillo.replacename.ApiResult
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeparturesRepository @Inject constructor() {

    suspend fun get(): ApiResult<DeparturesData> {
        delay(DELAY)
        return ApiResult.Success(DeparturesData)
    }
}

private const val DELAY = 1000L
