package com.example.tugasapkabsensi.restApi

import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.response.AbsensiResponse
import com.example.tugasapkabsensi.restApi.response.GuruMapelResponse
import com.example.tugasapkabsensi.restApi.response.LogInResponseSiswa
import com.example.tugasapkabsensi.restApi.response.SiswaResponse
import retrofit2.http.*

interface UserService {
    @POST("api/dataSiswa/logInSiswa")
    suspend fun logInSIswa(
        @Body submit: LogInSubmitSiswa,
    ): LogInResponseSiswa

    @GET("/profileSiswa")
    suspend fun getProfilSiswa(
        @Header("token") token: String,
    ): SiswaResponse

    @GET("api/dataAbsensi")
    suspend fun getAbsensi(
        @Header("token") token: String,
    ): AbsensiResponse

    @GET("api/dataGuruMapel")
    suspend fun getGuruMapel(
        @Header("token") token: String,
    ): GuruMapelResponse
}