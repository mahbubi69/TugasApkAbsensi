package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class ProsesAbsensiModel(
    @SerializedName("id_absen")
    val idAbsen: Int,
    @SerializedName("id_guru_mapel")
    val idGuruMapel: Int,
    @SerializedName("id_siswa")
    val idSIswa: Int?,
    @SerializedName("id_jurusan_kelas")
    val idJurusanKelas: Int,
    @SerializedName("hasil_absen")
    val hasilAbsen: String?,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("tgl_absen")
    val tglAbsen: String,
)
