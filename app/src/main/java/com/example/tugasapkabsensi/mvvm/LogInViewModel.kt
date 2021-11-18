package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.restApi.response.LogInResponseSiswa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val siswaRepository: SiswaRepository) :
    ViewModel() {
    fun logInSiswaVm(submitSiswa: LogInSubmitSiswa): LiveData<ApiResponseSiswa<LogInResponseSiswa>> =
        runBlocking {
            val logInJob = async { siswaRepository.logInSiswaRepo(submitSiswa) }
            runBlocking {
                logInJob.await().asLiveData()
            }
        }
}