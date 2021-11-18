package com.example.tugasapkabsensi.restApi.response

import com.google.gson.annotations.SerializedName

data class LogInResponseSiswa(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
)
