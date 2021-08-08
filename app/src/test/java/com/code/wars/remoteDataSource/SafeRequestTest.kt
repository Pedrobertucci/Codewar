package com.code.wars.remoteDataSource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class SafeRequestTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `should received throwable null`() = runBlocking {
        assert(convertErrorBody(null)?.isNotEmpty() == true)
    }

    @Test
    fun `should received throwable with some value`() = runBlocking {
        val errorResponseBody = "Some exception".toResponseBody("application/json".toMediaType())
        val error : Response<Any> = Response.error(500, errorResponseBody)
        val httpException = HttpException(error)
        assert(convertErrorBody(httpException)?.isNotEmpty() == true)
    }
}