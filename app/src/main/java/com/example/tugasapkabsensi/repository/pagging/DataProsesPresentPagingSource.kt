package com.example.tugasapkabsensi.repository.pagging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tugasapkabsensi.restApi.UserService
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import com.example.tugasapkabsensi.value.Value
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataProsesPresentPagingSource @Inject constructor(private val siswaService: UserService) {
    fun ListdDataProsesAbsen(
        token: String,
        idGuruMapel: Int,
    ): Flow<PagingData<ProsesAbsensiModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Value.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PagingProsesPresentSourceFactory(siswaService,
                    token,
                    idGuruMapel)
            }
        ).flow
    }
}