package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import com.example.tugasapkabsensi.restApi.model.UpdateProsesAbsenSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProsesPresesntViewModel @Inject constructor(
    private val siswaRepository: SiswaRepository,
) : ViewModel() {

    private var currentDataProsesAbsensi: Flow<PagingData<ProsesAbsensiModel>>? = null

    fun getDataProsesAbsensiMvvm(
        token: String,
        idGuruMapel: Int,
    ): Flow<PagingData<ProsesAbsensiModel>> {
        val lastResult = currentDataProsesAbsensi
        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<ProsesAbsensiModel>> =
            siswaRepository.getdataProsesAbsensi(token, idGuruMapel).cachedIn(viewModelScope)
        currentDataProsesAbsensi = newResult
        return newResult
    }

    fun updateProsesAbsen(
        token: String,
        idGuruMapel: Int,
        idSiswa: Int,
        submit: UpdateProsesAbsenSubmit,
    ): LiveData<ApiResponseSiswa<String>> =
        runBlocking {
            val updateJob =
                async { siswaRepository.updateProsesAbsen(token, idGuruMapel, idSiswa, submit) }
            runBlocking { updateJob.await().asLiveData() }
        }
}