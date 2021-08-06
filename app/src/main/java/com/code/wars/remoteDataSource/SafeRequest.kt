package com.code.wars.remoteDataSource

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class SafeResponse<out T> {
    data class Success<out T>(val value: T): SafeResponse<T>()
    data class GenericError(val code: Int, val error: String? = null): SafeResponse<Nothing>()
    object NetworkError: SafeResponse<Nothing>()
}

suspend fun <T> safeRequest(request: suspend () -> T): SafeResponse<T> {
    return try {
        val response = request.invoke()
        if ((response as Response<*>).isSuccessful) {
            SafeResponse.Success(response)
        } else {
            val error = (response as Response<*>)
            SafeResponse.GenericError(code = error.code(), error = error.errorBody()?.string())
        }

    } catch (exception: Exception) {
        when (exception) {
            is IOException -> SafeResponse.NetworkError
            is HttpException -> {
                val code = exception.code()
                SafeResponse.GenericError(code, convertErrorBody(exception))
            }
            else -> {
                SafeResponse.GenericError(500, convertErrorBody(null))
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException?): String? {
    return try {
        throwable?.response()?.errorBody().toString()
    } catch (exception: Exception) {
        ""
    }
}