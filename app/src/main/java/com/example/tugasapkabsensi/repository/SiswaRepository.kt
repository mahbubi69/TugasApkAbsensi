package com.example.tugasapkabsensi.repository

import androidx.paging.PagingData
import com.example.tugasapkabsensi.repository.pagging.DataPresentPagingSource
import com.example.tugasapkabsensi.repository.pagging.DataProsesPresentPagingSource
import com.example.tugasapkabsensi.restApi.UserService
import com.example.tugasapkabsensi.restApi.model.GuruMapelModel
import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.restApi.response.LogInResponseSiswa
import com.example.tugasapkabsensi.restApi.response.SiswaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SiswaRepository @Inject constructor(
    private val siswaService: UserService,
    private val dataPresentSourceGuruMapel: DataPresentPagingSource,
    private val dataProsesAbsensi: DataProsesPresentPagingSource,
) {

    suspend fun logInSiswaRepo(submit: LogInSubmitSiswa): Flow<ApiResponseSiswa<LogInResponseSiswa>> {
        return flow {
            emit(ApiResponseSiswa.Loading())
            try {
                emit(ApiResponseSiswa.Loading())
                val responseSubmitLoginSiswa = siswaService.logInSIswa(submit)
                if (responseSubmitLoginSiswa.status == 200) {
                    emit(ApiResponseSiswa.Succes(responseSubmitLoginSiswa))
                } else {
                    emit(ApiResponseSiswa.Error(responseSubmitLoginSiswa.message))
                }
            } catch (e: Exception) {
                emit(ApiResponseSiswa.Error(e.toString()))
                Timber.e("$e")
            }
        }.flowOn(Dispatchers.IO)
    }

    //get
    suspend fun getProfileSiswaRepo(
        token: String,
        idSiswa: Int,
    ): Flow<ApiResponseSiswa<SiswaResponse>> {
        return flow {
            emit(ApiResponseSiswa.Loading())
            try {
                emit(ApiResponseSiswa.Loading())
                val respSiswa = siswaService.getProfilSiswa(token, idSiswa)
                if (respSiswa.message == "Success") {
                    emit(ApiResponseSiswa.Succes(respSiswa))
                } else {
                    emit(ApiResponseSiswa.Error(respSiswa.message))
                }
            } catch (e: Exception) {
                emit(ApiResponseSiswa.Error(e.toString()))
                Timber.e("$e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDataguruMapelRepo(token: String, idJurusanKelas: Int): Flow<PagingData<GuruMapelModel>> {
        return dataPresentSourceGuruMapel.ListDataGuruMapel(token, idJurusanKelas)
    }

    fun getdataProsesAbsensi(
        token: String,
        idGuruMapel: Int,
    ): Flow<PagingData<ProsesAbsensiModel>> {
        return dataProsesAbsensi.ListdDataProsesAbsen(token, idGuruMapel)
    }
}