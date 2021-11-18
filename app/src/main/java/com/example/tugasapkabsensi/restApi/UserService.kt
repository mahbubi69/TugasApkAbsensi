package com.example.tugasapkabsensi.restApi

import com.example.tugasapkabsensi.restApi.model.LogInSubmitSiswa
import com.example.tugasapkabsensi.restApi.response.LogInResponseSiswa
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    //login
    @POST("api/dataSiswa/logInSiswa")
    suspend fun logInSIswa(
        @Body submit: LogInSubmitSiswa,
    ): LogInResponseSiswa


}