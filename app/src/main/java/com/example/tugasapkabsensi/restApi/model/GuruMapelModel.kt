package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class GuruMapelModel(
    @SerializedName("id_guru_mapel")
    val idGuruMapel: Int,
    @SerializedName("id_guru")
    val idGuru: Int,
    @SerializedName("id_mapel")
    val idMapel: String,
)
