package com.example.tugasapkabsensi.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasapkabsensi.R
import java.lang.ref.WeakReference

class SplassScrennActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splass_screnn)
        val task = MyAsynTask(this)
        task.execute()
    }

    companion object {
        class MyAsynTask internal constructor(context: SplassScrennActivity) :
            AsyncTask<Void, Void, Void>() {

            private val activityRef: WeakReference<SplassScrennActivity> =
                WeakReference(context)

            override fun onPreExecute() {
                val activity = activityRef.get()
                if (activity == null || activity.isFinishing) return

            }

            override fun onPostExecute(result: Void?) {
                val activity = activityRef.get()
                if (activity == null || activity.isFinishing) return
                if (activity.isFinished()) {
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
            }

            override fun doInBackground(vararg p0: Void?): Void? {
                try {
                    Thread.sleep(1500)
                } catch (e: Exception) {
                }
                return null
            }
        }
    }

    private fun isFinished(): Boolean {
        val pref = applicationContext.getSharedPreferences("UserData", MODE_PRIVATE)
        return pref.getBoolean("LogIn Berhasil", true)
    }

}