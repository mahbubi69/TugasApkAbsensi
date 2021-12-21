package com.example.tugasapkabsensi.restApi.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)