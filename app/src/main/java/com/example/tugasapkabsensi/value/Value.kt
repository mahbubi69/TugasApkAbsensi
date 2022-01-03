package com.example.tugasapkabsensi.value

object Value {
    const val CHANNEL_NOTIFICATION_1 = "channel notification1"
    const val CHANNEL_NOTIFICATION_2 = "channel notification2"

    const val CHANNEL_NOTIFICATION_UPLOAD = "channel notification Upload"

    //link rest api
    const val BASE_URL = "http://192.168.20.109:8080"
    const val EDT_URL_IMG = "$BASE_URL/api/dataSiswa/updateImgSiswa/"

    //key shareprefens
    const val PREF_NAME = "siswaPref"
    const val KEY_BASE_TOKEN = "key.token"
    const val KEY_BASE_ID_GURU_MAPEL = "key.base.id.guru.mapel"
    const val KEY_BASE_ID_SISWA = "key.base.id.siswa"
    const val KEY_BASE_ID_JURUSAN_KELAS = "key.id.base.jurusan.kelas"

    //key paging
    const val DEFAULT_PAGE_INDEX = 1
    const val DEFAULT_PAGE_SIZE = 10
}