package com.example.myapplication.ui.ASBC

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class SyncService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val channelId = "service_demo"
        val channel =
            NotificationChannel(channelId, "Demo Service", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("Đang chạy nền...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            Thread.sleep(3000)
            val msg = "Sync data success ${System.currentTimeMillis()}"

            //  Lưu vào ContentProvider
            val values = ContentValues().apply { put("message", msg) }
            contentResolver.insert(MyContentProvider.CONTENT_URI, values)

            //  Gửi broadcast báo thành công
            val br = Intent("com.example.myapp.ABC")
            br.putExtra("data", msg)
            sendBroadcast(br)
        }.start()

        return START_NOT_STICKY
    }
}
