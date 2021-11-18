package com.example.tugasapkabsensi.util

import android.content.Context
import android.content.SharedPreferences
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.value.Value


class SharedPrefencSiswa(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(Value.PREF_NAME, Context.MODE_PRIVATE)

    private val editor = prefs.edit()
    private val defaultDesc = context.resources.getString(R.string.keperluan_token)

    fun setTokenSiswa(token: String) {
        editor.putString(Value.KEY_BASE_TOKEN, token)
        editor.apply()
    }

    val getTokenSiswa = prefs.getString(Value.KEY_BASE_TOKEN, "token")
}