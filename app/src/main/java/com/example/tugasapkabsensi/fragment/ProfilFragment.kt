package com.example.tugasapkabsensi.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tugasapkabsensi.databinding.FragmentProfilBinding
import com.example.tugasapkabsensi.mvvm.ProfileSiswaViewModel
import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val viewModelSiswa: ProfileSiswaViewModel by viewModels()

    private var token: String = ""
    lateinit var pref: SharedPrefencSiswa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPrefencSiswa(requireContext())
        token = pref.getToken ?: ""
        Timber.d("token is $token")

        initiateUserSiswa(token)
    }

    //memulai user siswa
    fun initiateUserSiswa(token: String) {
        viewModelSiswa.getProfileSiswa(token)
            .observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is ApiResponseSiswa.Loading -> {
                        println("loading")
                    }

                    is ApiResponseSiswa.Succes -> {
                        initiateViewUserSiswa(response.data.data)
                    }

                    is ApiResponseSiswa.Error -> {
                        println("error")
                    }
                    else -> {

                    }

                }
            })
    }

    //memulai get view data siswa
    fun initiateViewUserSiswa(siswa: SiswaProfilModel) {
        binding.tvNamaProfil.text = siswa.namaSiswa
        binding.tvNisn.text = siswa.nisn
        binding.tvTtl.text = siswa.tglLahir
        binding.tvAlamat.text = siswa.alamat
    }

//    fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}