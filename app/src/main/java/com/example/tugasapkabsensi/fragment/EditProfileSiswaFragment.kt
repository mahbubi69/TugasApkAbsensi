package com.example.tugasapkabsensi.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.FragmentEditProfileSiswaBinding
import com.example.tugasapkabsensi.mvvm.UpdateSiswaViewModel
import com.example.tugasapkabsensi.restApi.model.UpdateProfileSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import com.example.tugasapkabsensi.value.Value
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EditProfileSiswaFragment : Fragment() {

    private var _binding: FragmentEditProfileSiswaBinding? = null
    private val binding get() = _binding!!

    //mvvm
    private val viewModel: UpdateSiswaViewModel by viewModels()

    //prefens
    private lateinit var pref: SharedPrefencSiswa
    var token: String = ""
    var idSiswa: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditProfileSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPrefencSiswa(requireContext())
        token = pref.getToken ?: ""
        idSiswa = pref.getIdSiswa
        Timber.d("id siswa : $idSiswa")

        binding.edtIcBack.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileSiswaFragment_to_profilFragment2)
        }
        binding.btnSimpan.setOnClickListener {
            submitUpdate()
            Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show()
        }
    }

    fun submitUpdate() {
        val nama = binding.etEditNama.text.toString().trim()
        val alamat = binding.etEditAlamat.text.toString().trim()
        val password = binding.etEditPassword.text.toString().trim()
        val noHp = binding.etEditNoHp.text.toString().trim()

        val updateSiswa = UpdateProfileSubmit(
            namaSiswa = nama,
            alamat = alamat,
            password = password,
            noHp = noHp,
        )

        if (nama.isEmpty()) {
            binding.etEditNama.error = "nama harus diisi"
            binding.etEditNama.requestFocus()
            return
        }
        if (alamat.isEmpty()) {
            binding.etEditAlamat.error = "alamat harus diisi"
            binding.etEditAlamat.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etEditPassword.error = "password harus diisi"
            binding.etEditPassword.requestFocus()
            return
        }
        if (noHp.isEmpty()) {
            binding.etEditNoHp.error = "no Hp harus diisi"
            binding.etEditNoHp.requestFocus()
            return
        }

        initiateSubmitUpdateProfile(token, idSiswa!!, updateSiswa)
        Timber.d("token $token")
        Timber.d("idSiswa $idSiswa")
    }

    fun initiateSubmitUpdateProfile(
        token: String,
        idSiswa: Int,
        submitUpdateProfile: UpdateProfileSubmit,
    ) {
        viewModel.updateProfileSiswaVm(token, idSiswa, submitUpdateProfile)
            .observe(viewLifecycleOwner,
                Observer { update ->
                    when (update) {
                        is ApiResponseSiswa.Succes -> {
                            Timber.d("succes update ${update.data}")
                            view?.findNavController()?.popBackStack()
                        }
                        is ApiResponseSiswa.Error -> {
                            showErrorDialog(update.errorMessage)
                        }
                        else -> {
                            Timber.d("Unknow Error")
                        }
                    }
                })
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("ada kesalahan!!")
            setMessage(message)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}