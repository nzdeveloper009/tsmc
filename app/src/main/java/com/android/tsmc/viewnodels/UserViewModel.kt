package com.android.tsmc.viewnodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tsmc.data.models.EditUserResponse
import com.android.tsmc.data.models.MessageResponse
import com.android.tsmc.data.models.StudentCreateRequest
import com.android.tsmc.data.models.StudentCreateResponse
import com.android.tsmc.data.models.StudentEditRequest
import com.android.tsmc.data.models.User
import com.android.tsmc.repo.UserRepository
import com.android.tsmc.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val studentCreateResponseLiveData: LiveData<NetworkResult<StudentCreateResponse>>
        get() = userRepository.studentCreateResponseLiveData

    val getAllStudentsResponseLiveData: LiveData<NetworkResult<List<User>>>
        get() = userRepository.getAllStudentResponseLiveData
    val deleteStudentsByIdResponseLiveData: LiveData<NetworkResult<MessageResponse>>
        get() = userRepository.deleteUserByIdResponseLiveData
    val editStudentsByIdResponseLiveData: LiveData<NetworkResult<EditUserResponse>>
        get() = userRepository.editUserInformationResponseLiveData


    fun createStudent(token:String,studentCreateRequest: StudentCreateRequest) {
        viewModelScope.launch {
            userRepository.createStudent(token,studentCreateRequest)
        }
    }
    fun getAllStudents(token:String) {
        viewModelScope.launch {
            userRepository.getAllStudents(token)
        }
    }
    fun deleteStudentById(token:String,userID:String) {
        viewModelScope.launch {
            userRepository.deleteUserById(token,userID)
        }
    }
    fun  editStudentInformation(token:String,userID:String,studentEditRequest: StudentEditRequest) {
        viewModelScope.launch {
            userRepository.editUserInformation(token,userID,studentEditRequest)
        }
    }


}