package com.code.wars.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wars.models.UserResponse
import com.code.wars.remoteDataSource.SafeResponse
import com.code.wars.repositories.UserRepository
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val userMutableData = MutableLiveData<UserResponse>()
    val userLiveData: LiveData<UserResponse> get() = userMutableData

    private val emptyValuesMutableData = MutableLiveData<Boolean>()
    val emptyValuesLiveData: LiveData<Boolean> get() = emptyValuesMutableData

    private val errorMutableData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = errorMutableData

    private val networkErrorMutableData = MutableLiveData<Boolean>()
    val networkErrorLiveData: LiveData<Boolean> get() = networkErrorMutableData

    private val loadingMutableData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingMutableData

    fun getUser(@NotNull user: String) {
        viewModelScope.launch {
            loadingMutableData.value = true

            when(val response = repository.getUser(user)) {
                is SafeResponse.NetworkError -> {
                    loadingMutableData.value = false
                    networkErrorMutableData.value = true
                }

                is SafeResponse.GenericError -> {
                    loadingMutableData.value = false
                    errorMutableData.value = response.error
                }

                is SafeResponse.Success -> {
                    loadingMutableData.value = false

                    response.data.body()?.let {
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
}