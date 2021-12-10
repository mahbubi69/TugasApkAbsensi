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

    fun setIdSiswa(prefIdSiswa: String, idSiswa: Int) {
        editor.putInt(prefIdSiswa, idSiswa)
        editor.apply()
    }

    fun setIdDataGuruMapel(prefIdGuruMapel: String, idGuruMapel: Int) {
        editor.putInt(prefIdGuruMapel, idGuruMapel)
        editor.apply()
    }

    fun clearTokenSiswa(prefKey: String) {
        editor.remove(prefKey)
        editor.apply()
    }

    fun clearIdDataGuruMapel(prefUdGuruMapel: String) {
        editor.remove(prefUdGuruMapel)
        editor.apply()
    }

    val getToken = prefs.getString(Value.KEY_BASE_TOKEN, "")
    val getIdGuruMapel = prefs.getInt(Value.KEY_BASE_ID_GURU_MAPEL, 0)
    val getIdSiswa = prefs.getInt(Value.KEY_BASE_ID_SISWA, 0)

}