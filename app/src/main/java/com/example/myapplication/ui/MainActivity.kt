package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.editImage.EditImageActivity
import com.example.myapplication.ui.editVideo.EditVideoActivity
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()
    private val adapter = PostAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloadimage.setOnClickListener {
            startActivity(Intent(this, EditImageActivity::class.java))
        }

        binding.btnloadvideo.setOnClickListener {
            startActivity(Intent(this, EditVideoActivity::class.java))
        }

        setupRecyclerView()
        observeViewModel()
        viewModel.fetchPosts()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.post.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.error.observe(this) { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }
}