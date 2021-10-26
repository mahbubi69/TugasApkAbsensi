package com.example.tugasapkabsensi.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.fragment.findNavController
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.activity.MainActivity1
import com.example.tugasapkabsensi.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    //notif
    private lateinit var noticationManager: NotificationManagerCompat
    val textTitle = "button"
    val textDesc = "button d klik"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticationManager = NotificationManagerCompat.from(requireContext())

        submitLogIn()
    }

    fun submitLogIn() {
        binding.btnLogIn.setOnClickListener {
            val username = binding.etUsername.toString().trim()
            val password = binding.etPassword.toString().trim()

            val inten = Intent(requireContext(), MainActivity1::class.java)
            startActivity(inten)

            if (username.isEmpty()) {
                binding.etUsername.error = "username tidak boleh kosong"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "password tidak boleh kosong"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}