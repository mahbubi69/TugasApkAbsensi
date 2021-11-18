package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class LogInSubmitSiswa(
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("password")
    val password: String,
)
