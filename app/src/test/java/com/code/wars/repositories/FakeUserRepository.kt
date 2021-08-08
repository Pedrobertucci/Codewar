package com.code.wars.repositories

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.utils.FilePath
import com.google.gson.Gson
import retrofit2.Response
import java.lang.Exception

class FakeUserRepository : UserRepository {

    var shouldReturnNetworkError = false
    var shouldReturnHTTPError = false
    var shouldReturnEmptyValues = false

    override suspend fun getUser(user: String): SafeResponse<Response<UserResponse>> {
        return when {
            shouldReturnNetworkError -> {
                SafeResponse.NetworkError("Network Error")
            }

            shouldReturnHTTPError -> {
                SafeResponse.GenericError(404, "Generic error")
            }

            shouldReturnEmptyValues -> {
                SafeResponse.Success(Response.success(UserResponse()))
            }

            else -> {
                val content = FilePath.getContent("user.json")
                val response = Gson().fromJson(content, UserResponse::class.java)
                SafeResponse.Success(Response.success(response))
            }
        }
    }

    override suspend fun getCompletedCodeChallenge(user: String): SafeResponse<Response<CompletedResponse>> {
        return when {
            shouldReturnNetworkError -> {
                SafeResponse.NetworkError("Network Error")
            }

            shouldReturnHTTPError -> {
                SafeResponse.GenericError(404, "Generic error")
            }

            shouldReturnEmptyValues -> {
                SafeResponse.Success(Response.success(CompletedResponse()))
            }

            else -> {
                try {
                    val content = FilePath.getContent("completed.json")
                    val response = Gson().fromJson(content, CompletedResponse::class.java)
                    SafeResponse.Success(Response.success(response))
                } catch (e: Exception) {
                    SafeResponse.GenericError(500, "Some problem occurred")
                }
            }
        }
    }

    override suspend fun getAuthoredChallenges(user: String): SafeResponse<Response<AuthoredResponse>> {
        return when {
            shouldReturnNetworkError -> {
                SafeResponse.NetworkError("Network Error")
            }

            shouldReturnHTTPError -> {
                SafeResponse.GenericError(404, "Generic error")
            }

            shouldReturnEmptyValues -> {
                SafeResponse.Success(Response.success(AuthoredResponse()))
            }

            else -> {
                try {
                    val content = FilePath.getContent("authored.json")
                    val response = Gson().fromJson(content, AuthoredResponse::class.java)
                    SafeResponse.Success(Response.success(response))

                } catch (e: Exception) {
                    SafeResponse.GenericError(500, "Some problem occurred")
                }
            }
        }
    }
}