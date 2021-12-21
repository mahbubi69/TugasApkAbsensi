package com.example.tugasapkabsensi.value

import com.denzcoskun.imageslider.models.SlideModel
import com.example.tugasapkabsensi.R
import java.util.ArrayList

//link foto url home

class DataSliderItemHome {
    fun ListFotoHomeMan(): ArrayList<SlideModel> {
        val dataListFoto = ArrayList<SlideModel>()

        dataListFoto.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTh6jXtxtiX9zGy8k8twM8u-k9IOm9We2O2naMdSAKZgWkFLSaIDqD4sMk-zv0LWw7m0tw&usqp=CAU",
            "Halaman Man2 Sitbondo"))

        dataListFoto.add(SlideModel("https://foto2.data.kemdikbud.go.id/getImage/20584620/1.jpg",
            "Halaman depan Man 2 Situbondo"))

        dataListFoto.add(SlideModel("https://disdik.sinjaikab.go.id/wp-content/uploads/2019/11/15-man-2.jpg",
            "Upacara"))

        return dataListFoto
    }
}