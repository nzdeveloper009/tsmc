package com.android.tsmc.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.android.tsmc.R
import com.android.tsmc.data.adapter.StudentAdapter
import com.android.tsmc.data.models.StudentCreateRequest
import com.android.tsmc.data.models.StudentEditRequest
import com.android.tsmc.data.models.User
import com.android.tsmc.databinding.FragmentStudentBinding
import com.android.tsmc.ui.base.BaseFragment
import com.android.tsmc.utils.AppPreferences
import com.android.tsmc.utils.NetworkResult
import com.android.tsmc.viewnodels.AuthViewModel
import com.android.tsmc.viewnodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFragment : BaseFragment() {
    private lateinit var binding: FragmentStudentBinding
    private lateinit var studentList: ArrayList<User>
    private lateinit var studentAdapter: StudentAdapter
    private val userViewMode by viewModels<UserViewModel>()
    private lateinit var createStudentRequest: StudentCreateRequest
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        binding = FragmentStudentBinding.inflate(layoutInflater)

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /* set list*/
        studentList = ArrayList()

        /*userAdapter*/
        studentAdapter = StudentAdapter(requireContext(), studentList, userViewMode)
        /*set recycler view adapter*/
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = studentAdapter
        /*set Dialog*/
        binding.addBtn.setOnClickListener { addInfo() }
        userViewMode.getAllStudents(AppPreferences.token)

        bindObserver()
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(requireContext())
        val v = inflater.inflate(R.layout.add_students, null)

        /* Set view */
        val StuName = v.findViewById<EditText>(R.id.userName)
        val StuMail = v.findViewById<EditText>(R.id.userMail)
        val StuPass = v.findViewById<EditText>(R.id.userPassword)

        val addDialog = AlertDialog.Builder(requireContext())
        addDialog.setView(v)

        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val name = StuName.text.toString()
            val mail = StuMail.text.toString()
            val pass = StuPass.text.toString()

            if (name.isNotEmpty() && mail.isNotEmpty() && pass.isNotEmpty()) {
                createStudentRequest = StudentCreateRequest(mail, pass, "student", name)
                userViewMode.createStudent(AppPreferences.token, createStudentRequest)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_LONG)
                    .show()
            }
        }

        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_LONG).show()
        }

        addDialog.create()
        addDialog.show()
    }

    private fun bindObserver() {
        userViewMode.studentCreateResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data != null && it.data.message.isNotEmpty()) {
                        Toast.makeText(requireContext(), "Successful Created", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {

                    Toast.makeText(requireContext(), "Processing", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
        userViewMode.getAllStudentsResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data != null) {
                        studentList.addAll(it.data)
                        studentAdapter.notifyDataSetChanged()
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {

                    Toast.makeText(requireContext(), "Getting Students", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
        userViewMode.deleteStudentsByIdResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data != null) {
                        Toast.makeText(requireContext(),"Student Remove Successful",Toast.LENGTH_LONG).show()
                        studentAdapter.notifyDataSetChanged()
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {

                    Toast.makeText(requireContext(), "Removing Students", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
        userViewMode.editStudentsByIdResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data != null) {
                        Toast.makeText(requireContext(),"Student Edit Successful",Toast.LENGTH_LONG).show()
                        studentAdapter.notifyDataSetChanged()
                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(requireContext(), "Error!! ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {

                    Toast.makeText(requireContext(), "Editing Students", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    }


}