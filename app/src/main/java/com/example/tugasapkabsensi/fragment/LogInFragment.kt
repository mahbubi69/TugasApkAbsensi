package com.example.tugasapkabsensi.fragment

import android.app.Notification
import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.FragmentLogInBinding
import com.example.tugasapkabsensi.value.Value


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
//    private fun notif() {
//        val build = NotificationCompat.Builder(requireContext(), Value.CHANNEL_NOTIFICATION_1)
//            .setSmallIcon(R.drawable.ic_novif)
//            .setContentTitle(textTitle)
//            .setContentText(textDesc)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//        val notification: Notification = build.build()
//        noticationManager.notify(1, notification)
//    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}