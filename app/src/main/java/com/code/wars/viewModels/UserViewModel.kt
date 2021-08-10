package com.code.wars.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wars.models.AuthoredResponse
import com.code.wars.models.CompletedResponse
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository) : ViewModel() {

    private val userMutableData = MutableLiveData<UserResponse>()
    val userLiveData: LiveData<UserResponse> get() = userMutableData

    private val completedMutableData = MutableLiveData<CompletedResponse>()
    val completedLiveData: LiveData<CompletedResponse> get() = completedMutableData

    private val authoredMutableData = MutableLiveData<AuthoredResponse>()
    val authoredLiveData: LiveData<AuthoredResponse> get() = authoredMutableData

    private val emptyValuesMutableData = MutableLiveData<Boolean>()
    val emptyValuesLiveData: LiveData<Boolean> get() = emptyValuesMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    fun getUser(@NotNull user: String) {
        viewModelScope.launch {
            when(val response = repository.getUser(user)) {
                is SafeResponse.NetworkError -> {
                    errorMutableData.value = "Network Error"
                }

                is SafeResponse.GenericError -> {
                    errorMutableData.value = response.error
                }

                is SafeResponse.Success -> {
                    response.value.body()?.let {
                        if (it.username.isNotEmpty())
                            userMutableData.value = it
                        else
                            emptyValuesMutableData.value = true
                    } ?: run {
                        emptyValuesMutableData.value = true
                    }
                }
            }
        }
    }

    fun getCompletedCodeChallenge(@NotNull user: String) {
        Log.d("completedSteps", "getCompletedCodeChallenge: $user")
        viewModelScope.launch {
            delay(500)
            when(val response = repository.getCompletedCodeChallenge(user)) {
                is SafeResponse.NetworkError -> {
                    errorMutableData.value = "Network Error"
                }

                is SafeResponse.GenericError -> {
                    errorMutableData.value = response.error
                }

                is SafeResponse.Success -> {
                    response.value.body()?.let {
                        if (it.completed.isEmpty())
                            emptyValuesMutableData.value = true
                        else
                            completedMutableData.value = it
                    } ?: run {
                        emptyValuesMutableData.value = true
                    }
                }
            }
        }
    }

    fun getAuthoredChallenges(@NotNull user: String) {
        viewModelScope.launch {
            when(val response = repository.getAuthoredChallenges(user)) {
                is SafeResponse.NetworkError -> {
                    errorMutableData.value = "Network Error"
                }

                is SafeResponse.GenericError -> {
                    errorMutableData.value = response.error
                }

                is SafeResponse.Success -> {
                    response.value.body()?.let {
                        if (it.challenges.isEmpty())
                            emptyValuesMutableData.value = true
                        else
                            authoredMutableData.value = it
                    } ?: run {
                        emptyValuesMutableData.value = true
                    }
                }
            }
        }
    }
}