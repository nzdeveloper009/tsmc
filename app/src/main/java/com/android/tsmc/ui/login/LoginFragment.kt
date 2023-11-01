package com.android.tsmc.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.databinding.FragmentLoginBinding
import com.android.tsmc.ui.base.BaseFragment
import com.android.tsmc.ui.controller.NavControllerActivity
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
        if(AppPreferences.isUserLoggedIn) {
            NavControllerActivity.start(requireContext(),AppPreferences.userType)
        }
        loginBinding.signInBtn.setOnClickListener {
            val email = loginBinding.emailEt.text.toString().trim()
            val password = loginBinding.passwordEt.text.toString().trim()
            if (isNotValidCredential(email, password)) return@setOnClickListener
            authViewMode.userLogin(
                UserLoginRequest(
                    email,
                    password
                )
            )
        }

    }

    private fun isNotValidCredential(email: String, password: String): Boolean {
        if (email.isEmpty() && password.isEmpty()) {
            loginBinding.emailTIL.error = "Fill the Field"
            loginBinding.passwordTIL.error = "Fill the Field"
            return true
        }
        if (email.isEmpty()) {
            loginBinding.emailTIL.error = "Fill the Field"
            return true
        } else {
            loginBinding.emailTIL.error = null
        }
        if (password.isEmpty()) {
            loginBinding.passwordTIL.error = "Fill the Field"
            return true
        } else {
            loginBinding.passwordTIL.error = null
        }
        return false
    }

    private fun bindObserver() {
        authViewMode.userLoginResponseLiveData.observe(viewLifecycleOwner, Observer {
            loginBinding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG).show()
                    if(it.data != null) {
                        AppPreferences.isUserLoggedIn = true
                        AppPreferences.userId = it.data.user._id
                        AppPreferences.userName = it.data.user.username
                        AppPreferences.userType = it.data.user.role
                        AppPreferences.token = it.data.token

                        NavControllerActivity.start(requireContext(),it.data.user.role)
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {
                    loginBinding.progressBar.isVisible = true
                }
            }
        })

    }

}