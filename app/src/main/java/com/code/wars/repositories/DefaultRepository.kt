package com.code.wars.repositories

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.RemoteDataSource
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.remoteDataSource.safeRequest
import retrofit2.Response

class DefaultRepository constructor(
                        private val remoteDataSource: RemoteDataSource) : UserRepository {
    private var page = 0

    override suspend fun getUser(user: String): SafeResponse<Response<UserResponse>> {
        return safeRequest {
            remoteDataSource.getUser(user = user)
        }
    }

    override suspend fun getCompletedCodeChallenge(user: String): SafeResponse<Response<CompletedResponse>> {
        val response = safeRequest {
            remoteDataSource.getCompletedCodeChallenges(user = user, page = page)
        }

        if (response is SafeResponse.Success) {
            page++
        }

        return response
    }

    override suspend fun getAuthoredChallenges(user: String): SafeResponse<Response<AuthoredResponse>> {
       return safeRequest {
           remoteDataSource.getAuthoredChallenges(user = user)
       }
    }

    fun getPage(): Int {
        return page
    }
}