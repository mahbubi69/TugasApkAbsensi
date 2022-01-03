package com.example.tugasapkabsensi.restApi.model

import com.google.gson.annotations.SerializedName

data class DeletImageSiswaSubmit(
    @SerializedName("Images")
    val images: String,
)