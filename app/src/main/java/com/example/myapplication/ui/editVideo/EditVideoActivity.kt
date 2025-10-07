package com.example.myapplication.ui.editVideo

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.editImage.ImageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditVideoActivity : AppCompatActivity() {
    private lateinit var adapter: ImageAdapter
    private val REQUEST_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_video)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = ImageAdapter(emptyList())
        recyclerView.adapter = adapter

        checkPermissionAndLoadImages()
    }

    private fun checkPermissionAndLoadImages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_PERMISSION)
            } else {
                loadVideos()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION)
            } else {
                loadVideos()
            }
        } else {
            loadVideos()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadVideos()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadVideos() {
        lifecycleScope.launch(Dispatchers.IO) {
            val videos = mutableListOf<Uri>()
            val projection = arrayOf(MediaStore.Video.Media._ID)
            val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                    )
                    videos.add(contentUri)
                }
            }

            withContext(Dispatchers.Main) {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = GridLayoutManager(this@EditVideoActivity, 2)
                val adapter = VideoAdapter(videos)
                recyclerView.adapter = adapter
            }
        }
    }

}