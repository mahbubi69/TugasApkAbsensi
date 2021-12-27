package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.UpdateImagesubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UpdateImageViewModel @Inject constructor(private val siswaRepository: SiswaRepository) :
    ViewModel() {
    fun updateImageSiswaVm(
        token: String,
        idSIswa: Int,
        submitImage: UpdateImagesubmit,
    ): LiveData<ApiResponseSiswa<String>> = runBlocking {
        val updateJob =
            async { siswaRepository.updateImage(token, idSIswa, submitImage) }
        runBlocking {
            updateJob.await().asLiveData()
        }
    }
}