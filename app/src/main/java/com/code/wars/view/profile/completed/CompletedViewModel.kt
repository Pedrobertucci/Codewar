package com.code.wars.view.profile.completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wars.models.CompletedResponse
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.repositories.UserRepository
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class CompletedViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val completedMutableData = MutableLiveData<CompletedResponse>()
    val completedLiveData: LiveData<CompletedResponse> get() = completedMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    private val networkErrorMutableData = MutableLiveData<Boolean>()
    val networkErrorLiveData: LiveData<Boolean> get() = networkErrorMutableData

    private val loadingMutableData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingMutableData

    private val emptyValuesMutableData = MutableLiveData<Boolean>()
    val emptyValuesLiveData: LiveData<Boolean> get() = emptyValuesMutableData

    fun getCompletedCodeChallenge(@NotNull user: String) {
        viewModelScope.launch {
            loadingMutableData.value = true
            when(val response = repository.getCompletedCodeChallenge(user)) {
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
}