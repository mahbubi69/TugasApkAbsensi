package com.example.tugasapkabsensi.restApi

import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.response.AbsensiResponse
import com.example.tugasapkabsensi.restApi.response.GuruMapelResponse
import com.example.tugasapkabsensi.restApi.response.LogInResponseSiswa
import com.example.tugasapkabsensi.restApi.response.SiswaResponse
import retrofit2.http.*

interface UserService {
    //login
    @POST("api/dataSiswa/logInSiswa")
    suspend fun logInSIswa(
        @Body submit: LogInSubmitSiswa,
    ): LogInResponseSiswa

    //profile
    @GET("/profileSiswa/{id_siswa}")
    suspend fun getProfilSiswa(
        @Header("token") token: String,
        @Path("id_siswa") idSiswa: Int,
    ): SiswaResponse

    //proses present
    @GET("api/dataAbsensi/{id_guru_mapel}")
    suspend fun getAbsensi(
        @Header("token") token: String,
        @Path("id_guru_mapel") idGuruMapel: Int,
    ): AbsensiResponse

    //present
    @GET("api/dataGuruMapel")
    suspend fun getGuruMapel(
        @Header("token") token: String,
    ): GuruMapelResponse


}