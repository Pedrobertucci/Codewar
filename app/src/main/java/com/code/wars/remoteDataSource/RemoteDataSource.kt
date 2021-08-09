package com.code.wars.remoteDataSource

import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDataSource {

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user}")
    suspend fun getUser(
        @Path("user") user: String
    ) : Response<UserResponse>

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user}/code-challenges/completed")
    suspend fun getCompletedCodeChallenges(
        @Path("user") user: String,
        @Query("page") page: Int,
    ) : Response<CompletedResponse>

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user}/code-challenges/authored")
    suspend fun getAuthoredChallenges(
        @Path("user") user: String,
    ) : Response<AuthoredResponse>
}