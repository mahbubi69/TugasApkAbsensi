package com.example.tugasapkabsensi

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.tugasapkabsensi.value.Value
import dagger.hilt.android.HiltAndroidApp
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.logger.UploadServiceLogger
import net.gotev.uploadservice.okhttp.OkHttpStack
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class BaseApk : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        createNotification()
        createNotificationChannel()
        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = Value.CHANNEL_NOTIFICATION_UPLOAD,
            debug = BuildConfig.DEBUG
        )

        UploadServiceConfig.httpStack = OkHttpStack(getOkHttpClient())
        UploadServiceConfig.idleTimeoutSeconds = 60 * 5
        UploadServiceConfig.bufferSizeBytes = 4096
        UploadServiceLogger.setLogLevel(UploadServiceLogger.LogLevel.Debug)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                Value.CHANNEL_NOTIFICATION_UPLOAD,
                "channel_notification_upload",
                NotificationManager.IMPORTANCE_LOW
            )
            val managerNotification = getSystemService(NotificationManager::class.java)
            managerNotification?.createNotificationChannel(channel)
        }
    }

    //notification
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

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(300L, TimeUnit.SECONDS)
            .readTimeout(300L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor { message: String? ->
                if (message != null) {
                    Timber.tag("message").d(message)
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .cache(null)
            .build()
    }
}