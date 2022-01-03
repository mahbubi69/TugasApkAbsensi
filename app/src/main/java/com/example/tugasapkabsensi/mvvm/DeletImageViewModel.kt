package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.DeletImageSiswaSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DeletImageViewModel @Inject constructor(private val siswaRepository: SiswaRepository) :
    ViewModel() {
    fun deletImageSiswa(
        token: String,
        idSIswa: Int,
        submitDeletImage: DeletImageSiswaSubmit,
    ): LiveData<ApiResponseSiswa<String>> =
        runBlocking {
            val updateJob =
                async { siswaRepository.deletImageSiswa(token, idSIswa, submitDeletImage) }
            runBlocking {
                updateJob.await().asLiveData()
            }
        }

}