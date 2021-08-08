package com.code.wars.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.code.wars.remoteDataSource.FakeRemoteDataSource
import com.code.wars.remoteDataSource.SafeResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var defaultRepository: DefaultRepository

    private var remoteDataSource = FakeRemoteDataSource()

    @Before
    fun setUp() {
        defaultRepository = DefaultRepository(remoteDataSource)
    }

    @Test
    fun `should fetch user and return a generic error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true

        when(val response = defaultRepository.getUser(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
               assert(response.code >= 400)
            }

            is SafeResponse.Success -> {
                assert(response.value.body() == null)
            }
        }
    }

    @Test
    fun `should fetch user and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        when(val response = defaultRepository.getUser(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.body() == null)
            }
        }
    }

    @Test
    fun `should fetch user and return some user`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        when(val response = defaultRepository.getUser(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.body() != null)
            }
        }
    }

    @Test
    fun `should fetch user completed code challenge and return a error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true

        when(val response = defaultRepository.getCompletedCodeChallenge(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(response.code >= 400)
                assert(defaultRepository.getPage() == 0)
            }

            is SafeResponse.Success -> {
                assert(response.value.body() == null)
            }
        }
    }

    @Test
    fun `should fetch user complete code challenge and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        when(val response = defaultRepository.getCompletedCodeChallenge(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.body() == null)
                assert(defaultRepository.getPage() == 1)
            }
        }
    }

    @Test
    fun `should fetch user complete code challenge and return some user`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        when(val response = defaultRepository.getCompletedCodeChallenge(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.isSuccessful)
                assert(response.value.body() != null)
                assert(defaultRepository.getPage() == 1)
            }
        }
    }

    @Test
    fun `should fetch user authored challenge and return a error`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = true

        when(val response = defaultRepository.getAuthoredChallenges(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(response.code >= 400)
            }

            is SafeResponse.Success -> {
                assert(!response.value.isSuccessful)
            }
        }
    }

    @Test
    fun `should fetch user authored challenge and return empty`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = true

        when(val response = defaultRepository.getAuthoredChallenges(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.isSuccessful)
                assert(response.value.body() == null)
            }
        }
    }

    @Test
    fun `should fetch user authored challenge and return some user`() = runBlocking {
        remoteDataSource.shouldReturnNetworkError = false
        remoteDataSource.shouldReturnEmptyValues = false

        when(val response = defaultRepository.getAuthoredChallenges(user = "john123")) {
            is SafeResponse.NetworkError -> {
                assert(false)
            }

            is SafeResponse.GenericError -> {
                assert(false)
            }

            is SafeResponse.Success -> {
                assert(response.value.isSuccessful)
                assert(response.value.body() != null)
            }
        }
    }

}