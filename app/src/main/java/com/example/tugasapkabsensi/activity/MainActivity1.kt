package com.example.tugasapkabsensi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.ActivityMain1Binding

class MainActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityMain1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = Navigation.findNavController(this, R.id.nav_controler1)
        val navView = binding.btnNavigationNav1
        NavigationUI.setupWithNavController(navView, navHost)
    }
}