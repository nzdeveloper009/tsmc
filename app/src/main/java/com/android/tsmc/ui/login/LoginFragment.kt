package com.android.tsmc.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.android.tsmc.databinding.FragmentLoginBinding
import com.android.tsmc.ui.base.BaseFragment
import com.android.tsmc.utils.AppPreferences
import com.android.tsmc.utils.NetworkResult
import com.android.tsmc.viewnodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var loginBinding:FragmentLoginBinding
    private val authViewMode by viewModels<AuthViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        loginBinding = FragmentLoginBinding.inflate(layoutInflater)
        return loginBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindObserver()

    }

    private fun bindObserver() {
        authViewMode.userLoginResponseLiveData.observe(viewLifecycleOwner, Observer {
//            loginBinding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG).show()
                    if(it.data != null) {
                        AppPreferences.isUserLoggedIn = true
                        AppPreferences.userId = it.data.user._id
                        AppPreferences.userName = it.data.user.username
                        AppPreferences.userType = it.data.user.role

                        // move to dashboard
                        when (it.data.user.role) {
                            "admin" -> {
                                // move admin dashboard
                            }
                            "student" -> {
                                // move student dashboard
                            }
                            else -> {
                                // move teacher dashboard
                            }
                        }
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {
//                    loginBinding.progressBar.isVisible = true
                }
            }
        })

    }

}