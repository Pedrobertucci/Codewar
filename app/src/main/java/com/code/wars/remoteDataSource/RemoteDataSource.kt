package com.code.wars.remoteDataSource

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiContract: ApiContract) {

    suspend fun getUser(user: String) = safeRequest {
        apiContract.getUser(user = user)
    }

    suspend fun getCompletedCodeChallenges(user: String, page: Int) = safeRequest {
        apiContract.getCompletedCodeChallenges(user = user, page = page)
    }

    suspend fun getAuthoredChallenges(user: String) = safeRequest {
        apiContract.getAuthoredChallenges(user = user)
    }
}