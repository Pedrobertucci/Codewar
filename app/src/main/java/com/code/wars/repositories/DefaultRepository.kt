package com.code.wars.repositories

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.RemoteDataSource
import com.code.wars.remoteDataSource.SafeResponse
import retrofit2.Response

class DefaultRepository constructor(
    private val remoteDataSource: RemoteDataSource) : UserRepository {
    private var page = 0

    override suspend fun getUser(user: String): SafeResponse<Response<UserResponse>> {
        return remoteDataSource.getUser(user = user)
    }

    override suspend fun getCompletedCodeChallenge(user: String): SafeResponse<Response<CompletedResponse>> {
        val response = remoteDataSource.getCompletedCodeChallenges(user = user, page = page)

        if (response is SafeResponse.Success) {
            page++
        }

        return response
    }

    override suspend fun getAuthoredChallenges(user: String): SafeResponse<Response<AuthoredResponse>> {
        return remoteDataSource.getAuthoredChallenges(user = user)
    }

    fun getPage(): Int {
        return page
    }
}