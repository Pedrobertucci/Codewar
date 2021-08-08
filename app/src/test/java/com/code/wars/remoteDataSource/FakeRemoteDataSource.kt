package com.code.wars.remoteDataSource

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeRemoteDataSource : RemoteDataSource {
    var shouldReturnNetworkError = false
    var shouldReturnEmptyValues = false

    override suspend fun getUser(user: String): Response<UserResponse> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(null)
            }

            else -> {
                Response.success(UserResponse())
            }
        }
    }

    override suspend fun getCompletedCodeChallenges(user: String, page: Int): Response<CompletedResponse> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(null)
            }

            else -> {
                Response.success(CompletedResponse())
            }
        }
    }

    override suspend fun getAuthoredChallenges(user: String): Response<AuthoredResponse> {
        return when {
            shouldReturnNetworkError -> {
                val errorResponseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
                Response.error(500, errorResponseBody)
            }

            shouldReturnEmptyValues -> {
                Response.success(null)
            }

            else -> {
                Response.success(AuthoredResponse())
            }
        }
    }
}