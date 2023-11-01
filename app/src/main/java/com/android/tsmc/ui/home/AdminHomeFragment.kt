package com.android.tsmc.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.android.tsmc.R
import com.android.tsmc.databinding.FragmentAdminHomeBinding
import com.android.tsmc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminHomeFragment : BaseFragment() {
    private lateinit var binding:FragmentAdminHomeBinding
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = FragmentAdminHomeBinding.inflate(layoutInflater)

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentsBtn.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToStudentFragment())
        }
        binding.teachersBtn.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToTeacherFragment())
        }
    }

}