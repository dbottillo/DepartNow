package com.dbottillo.replacename.feature.departures

import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.AppBuildConfig
import com.dbottillo.replacename.BusStopTimetableResponse
import com.dbottillo.replacename.DeparturesApiInterface
import com.dbottillo.replacename.StationTimetableResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class DeparturesRepositoryTest {

    private lateinit var repository: DeparturesRepository

    private var api: DeparturesApiInterface = mock()
    private var appBuildConfig = object : AppBuildConfig {
        override val transportAppKey: String
            get() = APP_KEY

        override val transportAppId: String
            get() = APP_ID
    }

    @BeforeEach
    fun setUp() {
        repository = DeparturesRepository(
            api = api,
            appBuildConfig = appBuildConfig
        )
    }

    @Test
    internal fun `should return data when station timetable API request is successful`() = runTest {
        val apiResponse = mock<StationTimetableResponse>()
        whenever(
            api.stationTimetable(
            station = "HRN",
            appId = appBuildConfig.transportAppId,
            appKey = appBuildConfig.transportAppKey,
            limit = 20,
            live = true,
            trainStatus = "passenger",
            type = "departure",
            callingAt = "FPK"
        )
        ).thenReturn(Response.success(apiResponse))

        val result = repository.getStationTimetable()

        assertThat(result).isEqualTo(ApiResult.Success(apiResponse))
    }

    @Test
    internal fun `should return error when station timetable API request is unsuccessful`() = runTest {
        val response = mock<Response<StationTimetableResponse>>()
        whenever(response.isSuccessful).thenReturn(false)
        whenever(response.code()).thenReturn(422)
        whenever(response.message()).thenReturn("error")
        whenever(
            api.stationTimetable(
            station = "HRN",
            appId = appBuildConfig.transportAppId,
            appKey = appBuildConfig.transportAppKey,
            limit = 20,
            live = true,
            trainStatus = "passenger",
            type = "departure",
            callingAt = "FPK"
        )
        ).thenReturn(response)

        val result = repository.getStationTimetable()

        assertThat(result is ApiResult.Error).isTrue()
        assertThat((result as ApiResult.Error).exception.message).isEqualTo("422 error")
    }

    @Test
    internal fun `should return data when bus timetable API request is successful`() = runTest {
        val apiResponse = mock<BusStopTimetableResponse>()
        whenever(
            api.busStopTimetable(
            busStop = "490011232S",
            appId = appBuildConfig.transportAppId,
            appKey = appBuildConfig.transportAppKey,
            limit = 20,
            live = true
        )
        ).thenReturn(Response.success(apiResponse))

        val result = repository.getBusDepartures()

        assertThat(result).isEqualTo(ApiResult.Success(apiResponse))
    }

    @Test
    internal fun `should return error when bus timetable API request is unsuccessful`() = runTest {
        val response = mock<Response<BusStopTimetableResponse>>()
        whenever(response.isSuccessful).thenReturn(false)
        whenever(response.code()).thenReturn(422)
        whenever(response.message()).thenReturn("error")
        whenever(
            api.busStopTimetable(
            busStop = "490011232S",
            appId = appBuildConfig.transportAppId,
            appKey = appBuildConfig.transportAppKey,
            limit = 20,
            live = true
        )
        ).thenReturn(response)

        val result = repository.getBusDepartures()

        assertThat(result is ApiResult.Error).isTrue()
        assertThat((result as ApiResult.Error).exception.message).isEqualTo("422 error")
    }
}

private const val APP_KEY = "APP_KEY"
private const val APP_ID = "APP_ID"
