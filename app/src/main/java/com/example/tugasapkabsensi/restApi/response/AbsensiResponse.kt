package com.example.tugasapkabsensi.restApi.response
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import com.google.gson.annotations.SerializedName

data class AbsensiResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("row_count")
    val rowCount: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ProsesAbsensiModel>,
)
