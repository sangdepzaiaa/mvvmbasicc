package com.example.myapplication.ui.ASBC

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAsbcactivityBinding

class ASBCActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAsbcactivityBinding.inflate(layoutInflater) }
    private lateinit var broadcastReceiver : BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        broadcastReceiver = object  : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                val msg = p1?.getStringExtra("data")
                Toast.makeText(this@ASBCActivity,msg, Toast.LENGTH_LONG).show()
            }
        }

        val intent = Intent(this, SyncService::class.java)
        startService(intent)

    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("ABC")
        registerReceiver(broadcastReceiver,intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}