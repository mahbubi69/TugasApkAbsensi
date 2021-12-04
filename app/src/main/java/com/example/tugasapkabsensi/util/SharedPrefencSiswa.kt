package com.example.tugasapkabsensi.util

import android.content.Context
import android.content.SharedPreferences
import com.example.tugasapkabsensi.value.Value


class SharedPrefencSiswa(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(Value.PREF_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setTokenSiswa(prefToken: String, token: String) {
        editor.putString(prefToken, token)
        editor.apply()
    }

    fun clearTokenSiswa(prefKey: String) {
        editor.remove(prefKey)
        editor.apply()
    }

    val getToken = prefs.getString(Value.KEY_BASE_TOKEN,"")
}