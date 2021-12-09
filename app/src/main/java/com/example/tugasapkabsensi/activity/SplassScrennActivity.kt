package com.example.tugasapkabsensi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import timber.log.Timber

class SplassScrennActivity : AppCompatActivity() {
    lateinit var pref: SharedPrefencSiswa
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splass_screnn)

        pref = SharedPrefencSiswa(this)
        token = pref.getToken ?: ""
        Timber.d(token)

        Handler(Looper.getMainLooper()).postDelayed({
            if (token.isBlank()) {
                val inten = Intent(this, MainActivity::class.java)
                startActivity(inten)
            } else {
                val inten = Intent(this, MainActivity1::class.java)
                startActivity(inten)
            }
            finish()
        }, 1500)

    }
}
