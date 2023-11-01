package com.android.tsmc.ui.teacher


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.android.tsmc.R
import com.android.tsmc.databinding.FragmentTeacherHomeBinding
import com.android.tsmc.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherFragment : BaseFragment() {

    private lateinit var binding:FragmentTeacherHomeBinding
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = FragmentTeacherHomeBinding.inflate(layoutInflater)
        return binding
    }

}