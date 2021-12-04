package com.example.tugasapkabsensi.repository.pagging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tugasapkabsensi.restApi.UserService
import com.example.tugasapkabsensi.restApi.model.GuruMapelModel
import com.example.tugasapkabsensi.value.Value
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataPagingSource @Inject constructor(private val siswaService: UserService) {
    fun ListDataGuruMapel(token: String): Flow<PagingData<GuruMapelModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Value.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PagingSourceFactory(siswaService, token) }
        ).flow
    }
}