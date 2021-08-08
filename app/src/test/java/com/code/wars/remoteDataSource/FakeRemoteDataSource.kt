package com.code.wars.remoteDataSource

import com.code.wars.models.*
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
                val completed = CompletedResponse()
                completed.completed.add(Completed())
                completed.completed.add(Completed())
                completed.completed.add(Completed())
                Response.success(completed)
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
                val authored = AuthoredResponse()
                authored.challenges.add(Challenge())
                authored.challenges.add(Challenge())
                authored.challenges.add(Challenge())
                Response.success(authored)
            }
        }
    }
}