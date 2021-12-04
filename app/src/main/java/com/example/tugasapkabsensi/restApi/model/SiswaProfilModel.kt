package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class SiswaProfilModel(
    @SerializedName("id_siswa")
    val idSiswa: Int,

    @SerializedName("image_siswa")
    val imageSiswa: String,

    @SerializedName("nama_siswa")
    val namaSiswa: String,

    @SerializedName("alamat")
    val alamat: String,

    @SerializedName("jenis_kelamin")
    val jenisKelamin: String,

    @SerializedName("tgl_lahir")
    val tglLahir: String,

    @SerializedName("nisn")
    val nisn: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("no_hp")
    val noHp: String,

    @SerializedName("id_jurusan_kelas")
    val idKelas: Int,

    @SerializedName("id_admin")
    val idJurusan: Int,
    )
