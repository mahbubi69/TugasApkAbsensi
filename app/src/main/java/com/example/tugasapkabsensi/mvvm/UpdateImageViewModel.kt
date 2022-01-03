package com.example.tugasapkabsensi.mvvm

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.UpdateImagesubmit
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
class UpdateImageViewModel @Inject constructor(private val siswaRepository: SiswaRepository) :
    ViewModel() {
    fun editImageProfile(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        token: String,
        idSIswa: Int,
        path: String,
    ) {
        MultipartUploadRequest(context, Value.EDT_URL_IMG + idSIswa)
            .setMethod("PUT")
            .addHeader("token", token)
            .setMaxRetries(2)
            .addFileToUpload(path, "image", contentType = "images/*")
            .subscribe(context = context, lifecycleOwner = lifecycleOwner, delegate = object :
                RequestObserverDelegate {
                override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
                    Timber.d("data Sudah $uploadInfo")
                }

                override fun onCompletedWhileNotObserving() {
                    Timber.d("harus data")
                }

                override fun onError(
                    context: Context,
                    uploadInfo: UploadInfo,
                    exception: Throwable,
                ) {
                    exception.stackTrace
                }

                override fun onProgress(context: Context, uploadInfo: UploadInfo) {
                    Timber.d("daye lagi proses")
                }

                override fun onSuccess(
                    context: Context,
                    uploadInfo: UploadInfo,
                    serverResponse: ServerResponse,
                ) {
                    Timber.d("data done terupload $uploadInfo,\n ${serverResponse.bodyString}")
                }

            }
            )
    }
}