package com.example.myapplication.ui.ASBC

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class SyncService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            delay(2000)
            val msg = "Sync data success ${System.currentTimeMillis()}"

            // ✅ Lưu vào ContentProvider
            val values = ContentValues().apply { put("message", msg) }
            contentResolver.insert(MyContentProvider.CONTENT_URI, values)

            // ✅ Gửi broadcast báo thành công
            val br = Intent("com.example.myapp.ABC")
            br.putExtra("data", msg)
            sendBroadcast(br)

            // tự stop
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
