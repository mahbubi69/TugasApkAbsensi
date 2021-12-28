package com.example.tugasapkabsensi.fragment


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.activity.MainActivity1
import com.example.tugasapkabsensi.databinding.FragmentLogInBinding
import com.example.tugasapkabsensi.mvvm.LogInViewModel
import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import com.example.tugasapkabsensi.value.Value
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    lateinit var sharedPrefencSiswa: SharedPrefencSiswa

    //mvvm
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        noticationManager = NotificationManagerCompat.from(requireContext())
        sharedPrefencSiswa = SharedPrefencSiswa(requireContext())
        submitLogIn()
    }

    //
    fun submitLogIn() {
        binding.btnLogIn.setOnClickListener {
            val nisn = binding.etNisn.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            Timber.d("chek nisn : $nisn")
            val logInSubmitSiswa = LogInSubmitSiswa(
                nisn, password
            )

            if (nisn.isEmpty()) {
                binding.etNisn.error = "nisn tidak boleh kosong"
                binding.etNisn.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "password tidak boleh kosong"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            initiateLogInSiswa(logInSubmitSiswa)
        }

    }

    private fun initiateLogInSiswa(submitSiswa: LogInSubmitSiswa) {
        viewModel.logInSiswaVm(submitSiswa).observe(viewLifecycleOwner, Observer { logIn ->
            when (logIn) {
                is ApiResponseSiswa.Loading -> {
                    showLoading(true)
                }

                is ApiResponseSiswa.Succes -> {
                    showLoading(false)
                    sharedPrefencSiswa.setTokenSiswa(Value.KEY_BASE_TOKEN, logIn.data.token)
                    sharedPrefencSiswa.setIdSiswa(Value.KEY_BASE_ID_SISWA, logIn.data.idUser)
                    sharedPrefencSiswa.setIdJurusanKelas(Value.KEY_BASE_ID_JURUSAN_KELAS,
                        logIn.data.idJurusanKelas)

                    val inten = Intent(requireContext(), MainActivity1::class.java)
                    startActivity(inten)
                    activity?.finish()

                    Timber.d("berhasil login : ${logIn.data.token}")
                    Timber.d("id siswa login : ${logIn.data.idUser}")
                }

                is ApiResponseSiswa.Error -> {
                    showLoading(false)
                    messageCustomDialogLogout(logIn.errorMessage)
                    Timber.d("Error: {${logIn.errorMessage}}")
                }
                else -> {
                    Timber.d("Unknow Error")
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.etNisn.isEnabled = false
            binding.etPassword.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.etNisn.isEnabled = true
            binding.etPassword.isEnabled = true
        }
    }

    private fun messageCustomDialogLogout(message: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_login_message)

        val tvMessage = dialog.findViewById<TextView>(R.id.tv_login_message)
        val btnOK = dialog.findViewById<Button>(R.id.btn_ok_login_message)

        tvMessage.text = message
        btnOK.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}