package com.example.myapplication.ui.ASBC

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityAsbcactivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ASBCActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAsbcactivityBinding.inflate(layoutInflater) }
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    delay(2000)
                    withContext(Dispatchers.Main){
                        val msg = p1?.getStringExtra("data")
                        binding.tvHomeReceiver.text = msg
                    }
                }
            }

        }

        //  Khởi chạy Flow quan sát ContentProvider
        lifecycleScope.launch {
            observeProviderFlow(MyContentProvider.CONTENT_URI)
                .collect { msg ->
                    binding.tvHomeCPReceiver.text = msg
                }
        }

        val intent = Intent(this, SyncService::class.java)
        startService(intent)
    }


    fun Context.observeProviderFlow(uri: Uri): Flow<String> = callbackFlow {
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                launch(Dispatchers.IO) {
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    val msg = cursor?.use {
                        if (it.moveToLast()) it.getString(it.getColumnIndexOrThrow("message")) else null
                    }
                    msg?.let { trySend(it) }
                }
            }
        }
        contentResolver.registerContentObserver(uri, true, observer)
        awaitClose { contentResolver.unregisterContentObserver(observer) }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("com.example.myapp.ABC")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}
