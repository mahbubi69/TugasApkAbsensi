package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class UpdateProsesAbsenSubmit(
    @SerializedName("hasil_absen")
    val hasilAbsen: String,
)