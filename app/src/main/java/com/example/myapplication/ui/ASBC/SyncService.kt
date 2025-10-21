package com.example.myapplication.ui.ASBC

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class SyncService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread{
            Thread.sleep(3000)
            val br = Intent("ABC")
            br.putExtra("data","sync data success")
            sendBroadcast(br)
        }.start()

        return START_NOT_STICKY
    }

}