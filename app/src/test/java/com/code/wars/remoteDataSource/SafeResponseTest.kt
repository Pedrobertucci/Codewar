package com.code.wars.remoteDataSource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class SafeResponseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `should received return SafeResponse with success`() = runBlocking {
        val success = SafeResponse.Success(Response.success(""))
        assert(success.data.isSuccessful)
    }

    @Test
    fun `should received return SafeResponse with network exception`() = runBlocking {
        val networkError = SafeResponse.NetworkError("Network exception")
        assert(networkError.error?.isNotEmpty() == true)
    }

    @Test
    fun `should received return SafeResponse with generic error`() = runBlocking {
        val genericError = SafeResponse.GenericError(500, "Some exception")
        assert(genericError.error?.isNotEmpty() == true)
        assert(genericError.code >= 400)
    }
}