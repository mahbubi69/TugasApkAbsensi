package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.*
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.restApi.response.SiswaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileSiswaViewModel @Inject constructor(private val profilSiswaRepo: SiswaRepository) :
    ViewModel() {
    fun getProfileSiswa(token: String, idSiswa: Int): LiveData<ApiResponseSiswa<SiswaResponse>> = runBlocking{
        val userJob = async { profilSiswaRepo.getProfileSiswaRepo(token, idSiswa) }
        runBlocking {
            userJob.await().asLiveData()
        }
    }
}