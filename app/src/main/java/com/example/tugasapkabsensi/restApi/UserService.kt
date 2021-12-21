package com.example.tugasapkabsensi.restApi

import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.model.UpdateProfileSubmit
import com.example.tugasapkabsensi.restApi.response.*
import retrofit2.http.*

interface UserService {
    //login
    @POST("api/dataSiswa/logInSiswa")
    suspend fun logInSIswa(
        @Body submit: LogInSubmitSiswa,
    ): LogInResponseSiswa

    //profile
    @GET("api/profileSiswa/{id_siswa}")
    suspend fun getProfilSiswa(
        @Header("token") token: String,
        @Path("id_siswa") idSiswa: Int,
    ): SiswaResponse

    //update profile
    @PUT("api/dataSiswa/updateSiswa/{id_siswa}")
    suspend fun updateProfile(
        @Header("token") token: String,
        @Path("id_siswa") idSiswa: Int,
        @Body submit: UpdateProfileSubmit,
    ): UpdateProfileResponse

    //proses present (mapel present)
    @GET("api/dataAbsensi/{id_guru_mapel}")
    suspend fun getAbsensi(
        @Header("token") token: String,
        @Path("id_guru_mapel") idGuruMapel: Int,
    ): AbsensiResponse

    //present
    @GET("api/dataGuruMapel/{id_jurusan_kelas}")
    suspend fun getGuruMapel(
        @Header("token") token: String,
        @Path("id_jurusan_kelas") idJurusanKelas: Int,
    ): GuruMapelResponse

}