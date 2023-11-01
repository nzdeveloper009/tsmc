package com.android.tsmc.ui.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import com.android.tsmc.R
import com.android.tsmc.databinding.ActivityNavControllerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavControllerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNavControllerBinding
    companion object {
        fun start(context: Context, type:String){
            val intent = Intent(context,NavControllerActivity::class.java)
            intent.putExtra("TYPE",type)
            context.startActivity(intent)
        }
    }

    private lateinit var type:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = intent.getStringExtra("TYPE").toString()

        // move to home screen
        when (type) {
            "admin" -> {
                // inflate admin home

            }
            "student" -> {
                // inflate student home
            }
            else -> {
                // inflate teacher home
            }
        }
    }
}