package com.dbottillo.replacename.feature.home

import com.dbottillo.replacename.ApiInterface
import com.dbottillo.replacename.ApiResult
import com.dbottillo.replacename.Todo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class HomeRepositoryTest {

    private lateinit var underTest: HomeRepository

    private var api: ApiInterface = mock()

    @BeforeEach
    fun setUp() {
        underTest = HomeRepository(api)
    }

    @Test
    internal fun `should return data when API request is successful`() = runBlocking {
        val todo = mock<Todo>()
        whenever(api.getTodo(5)).thenReturn(Response.success(todo))

        val result = underTest.get()

        assertThat(result).isEqualTo(ApiResult.Success(todo))
    }

    @Test
    internal fun `should return error when API request is unsuccessful`() = runBlocking {
        val response = mock<Response<Todo>>()
        whenever(response.isSuccessful).thenReturn(false)
        whenever(response.code()).thenReturn(422)
        whenever(response.message()).thenReturn("error")
        whenever(api.getTodo(5)).thenReturn(response)

        val result = underTest.get()

        assertThat(result is ApiResult.Error).isTrue()
        assertThat((result as ApiResult.Error).exception.message).isEqualTo("422 error")
    }
}
