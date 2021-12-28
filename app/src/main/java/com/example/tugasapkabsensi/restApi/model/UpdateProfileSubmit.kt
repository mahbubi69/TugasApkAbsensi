package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileSubmit(
    @SerializedName("nama_siswa")
    val namaSiswa: String,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("no_hp")
    val noHp: String,
)