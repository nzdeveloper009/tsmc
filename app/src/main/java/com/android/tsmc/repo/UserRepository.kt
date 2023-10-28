package com.android.tsmc.repo


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.tsmc.data.api.UserApi
import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.data.models.UserLoginResponse
import com.android.tsmc.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi:UserApi){

    private val _userLoginResponseLiveData = MutableLiveData<NetworkResult<UserLoginResponse>>()
    val userLoginResponseLiveData: LiveData<NetworkResult<UserLoginResponse>>
        get() = _userLoginResponseLiveData


    suspend fun userLogin(userLoginRequest: UserLoginRequest) {
        _userLoginResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.userLogin(userLoginRequest)
        handleUserLoginResponse(response)
    }

    private fun handleUserLoginResponse(response: Response<UserLoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userLoginResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userLoginResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _userLoginResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}