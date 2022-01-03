package com.example.tugasapkabsensi.mvvm

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.UpdateProfileSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.value.Value
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpdateSiswaViewModel @Inject constructor(private val siswaRepository: SiswaRepository) :
    ViewModel() {
    fun updateProfileSiswaVm(
        token: String,
        idSIswa: Int,
        submitUpdateProfile: UpdateProfileSubmit,
    ): LiveData<ApiResponseSiswa<String>> =
        runBlocking {
            val updateJob =
                async { siswaRepository.updateProfileSiswa(token, idSIswa, submitUpdateProfile) }
            runBlocking {
                updateJob.await().asLiveData()
            }
        }

}