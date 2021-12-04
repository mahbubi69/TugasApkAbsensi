package com.example.tugasapkabsensi.restApi.response

import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.google.gson.annotations.SerializedName

data class SiswaResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("row_count")
    val rowCount: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: SiswaProfilModel,
)
