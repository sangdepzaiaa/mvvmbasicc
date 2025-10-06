package com.example.myapplication.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(){
    val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val postAdapter by lazy(LazyThreadSafetyMode.NONE){
        PostAdapter()
    }
    val viewmodel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        bindVM()
    }

    private fun setupView() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
        postAdapter.onItemClick = { post ->
            Toast.makeText(this, "Clicked ${post.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindVM() {
        viewmodel.post.observe(this,::render)
        viewmodel.error.observe(this){error ->
            error.let{
                Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun render(posts: List<Post>) {
        postAdapter.submitList(posts)
    }
}