package com.code.wars.repositories

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.SafeResponse
import retrofit2.Response

interface UserRepository {

    suspend fun getUser(user: String) : SafeResponse<Response<UserResponse>>

    suspend fun getCompletedCodeChallenge(user: String) : SafeResponse<Response<CompletedResponse>>

    suspend fun getAuthoredChallenges(user: String) : SafeResponse<Response<AuthoredResponse>>
}