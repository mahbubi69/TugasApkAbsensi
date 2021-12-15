package com.example.tugasapkabsensi.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tugasapkabsensi.repository.SiswaRepository
import com.example.tugasapkabsensi.restApi.model.GuruMapelModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GuruMapelViewModel @Inject constructor(
    private val siswaRepositoy: SiswaRepository,
) : ViewModel() {
    private var currentDataGuruMapel: Flow<PagingData<GuruMapelModel>>? = null

    fun getDataGuruMapelMvvm(token: String, idJurusanKelas: Int): Flow<PagingData<GuruMapelModel>> {
        val lastRersult = currentDataGuruMapel
        if (lastRersult != null) {
            return lastRersult
        }

        val newResult: Flow<PagingData<GuruMapelModel>> =
            siswaRepositoy.getDataguruMapelRepo(token, idJurusanKelas).cachedIn(viewModelScope)
        currentDataGuruMapel = newResult
        return newResult
    }
}