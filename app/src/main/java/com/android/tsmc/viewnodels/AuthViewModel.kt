package com.android.tsmc.viewnodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.data.models.UserLoginResponse
import com.android.tsmc.repo.UserRepository
import com.android.tsmc.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val userLoginResponseLiveData: LiveData<NetworkResult<UserLoginResponse>>
        get() = userRepository.userLoginResponseLiveData


    fun userLogin(userLoginRequest: UserLoginRequest) {
        viewModelScope.launch {
            userRepository.userLogin(userLoginRequest)
        }
    }
}