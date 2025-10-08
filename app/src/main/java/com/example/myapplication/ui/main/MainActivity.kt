package com.example.myapplication.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Post
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.dialog.dialog_help.showPostPopup
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(){
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val postAdapter by lazy{
        PostAdapter()
    }
    private val viewmodel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        bindVM()
    }

    private fun setupView() {
        binding.recyclerView.run {
            adapter = postAdapter
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) binding.fabAdd.hide()
                else if (dy < 0) binding.fabAdd.show()
            }
        })

        binding.fabAdd.setOnClickListener {
            showPostPopup(this, null, onSave = { title, body ->
                viewmodel.addPost(title, body)
            })
        }
        postAdapter.onItemClick = { post: Post ->
            showPostPopup(this, post,
                onSave = { title, body ->
                    viewmodel.updatePost(post, title, body)
                },
                onDelete = {
                    viewmodel.deletePost(post)
                }
            )
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