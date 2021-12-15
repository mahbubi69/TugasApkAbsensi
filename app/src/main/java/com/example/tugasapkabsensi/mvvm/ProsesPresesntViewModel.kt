package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
}