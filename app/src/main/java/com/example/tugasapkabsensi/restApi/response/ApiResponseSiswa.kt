package com.example.tugasapkabsensi.restApi.response

sealed class ApiResponseSiswa<out R> {
    class Loading<T>() : ApiResponseSiswa<T>()
    data class Succes<out T>(val data: T) : ApiResponseSiswa<T>()
    data class Error(val errorMessage: String) : ApiResponseSiswa<Nothing>()
    data class SuccesMessage(val succesMessage: String) : ApiResponseSiswa<Nothing>()
}
