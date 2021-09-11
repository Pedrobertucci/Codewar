package com.code.wars.view.profile.authored

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wars.models.AuthoredResponse
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.repositories.UserRepository
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class AuthoredViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val authoredMutableData = MutableLiveData<AuthoredResponse>()
    val authoredLiveData: LiveData<AuthoredResponse> get() = authoredMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    private val networkErrorMutableData = MutableLiveData<Boolean>()
    val networkErrorLiveData: LiveData<Boolean> get() = networkErrorMutableData

    private val loadingMutableData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingMutableData

    private val emptyValuesMutableData = MutableLiveData<Boolean>()
    val emptyValuesLiveData: LiveData<Boolean> get() = emptyValuesMutableData

    fun getAuthoredChallenges(@NotNull user: String) {
        viewModelScope.launch {
            loadingMutableData.value = true
            when(val response = repository.getAuthoredChallenges(user)) {
                is SafeResponse.NetworkError -> {
                    loadingMutableData.value = false
                    errorMutableData.value = "Network Error"
                }

                is SafeResponse.GenericError -> {
                    loadingMutableData.value = false
                    errorMutableData.value = response.error
                }

                is SafeResponse.Success -> {
                    loadingMutableData.value = false
                    response.data.body()?.let {
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