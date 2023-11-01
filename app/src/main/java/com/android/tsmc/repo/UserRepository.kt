package com.android.tsmc.repo


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.tsmc.data.api.UserApi
import com.android.tsmc.data.models.StudentCreateRequest
import com.android.tsmc.data.models.StudentCreateResponse
import com.android.tsmc.data.models.User
import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.data.models.UserLoginResponse
import com.android.tsmc.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userLoginResponseLiveData = MutableLiveData<NetworkResult<UserLoginResponse>>()
    val userLoginResponseLiveData: LiveData<NetworkResult<UserLoginResponse>>
        get() = _userLoginResponseLiveData


    private val _studentCreateResponseLiveData =
        MutableLiveData<NetworkResult<StudentCreateResponse>>()
    val studentCreateResponseLiveData: LiveData<NetworkResult<StudentCreateResponse>>
        get() = _studentCreateResponseLiveData


    private val _getAllStudentResponseLiveData =
        MutableLiveData<NetworkResult<List<User>>>()
    val getAllStudentResponseLiveData: LiveData<NetworkResult<List<User>>>
        get() = _getAllStudentResponseLiveData


    suspend fun userLogin(userLoginRequest: UserLoginRequest) {
        _userLoginResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.userLogin(userLoginRequest)
        handleUserLoginResponse(response)
    }


    suspend fun createStudent(token: String, studentCreateRequest: StudentCreateRequest) {
        _studentCreateResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.createStudent(token, studentCreateRequest)
        handleStudentCreateResponse(response)
    }

    suspend fun getAllStudents(token: String) {
        _getAllStudentResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.getAllStudent(token)
        handleGetAllStudentsResponse(response)
    }

    private fun handleGetAllStudentsResponse(response: Response<List<User>>) {
        if (response.isSuccessful && response.body() != null) {
            _getAllStudentResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _getAllStudentResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _getAllStudentResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    private fun handleStudentCreateResponse(response: Response<StudentCreateResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _studentCreateResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _studentCreateResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _studentCreateResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
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