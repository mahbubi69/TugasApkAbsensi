package com.example.tugasapkabsensi.notiif

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.tugasapkabsensi.value.Value

class NotifApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val build1 = NotificationChannel(
                Value.CHANNEL_NOTIFICATION_1,
                "channel_notification_1",
                NotificationManager.IMPORTANCE_HIGH
            )
            build1.description = " is notification 1"

            val managerNotification = getSystemService(NotificationManager::class.java)
            managerNotification?.createNotificationChannel(build1)
        }
    }
}