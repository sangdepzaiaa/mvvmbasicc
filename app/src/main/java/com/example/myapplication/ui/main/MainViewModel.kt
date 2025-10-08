package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository): ViewModel(){
    private val _post = MutableLiveData<List<Post>>()
    val post : LiveData<List<Post>> get() = _post

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> get() = _error

    init {
        fetchPosts()
    }

    fun fetchPosts(){
        viewModelScope.launch {
            try {
                val posts = repository.getPosts()
                _post.postValue(posts)

            }catch (e : Exception){
                _error.postValue("Error: ${e.message}")
            }
        }
    }
//    Nếu không chắc thread, dùng postValue(dùng được mọi thred)
//    Nếu chắc chắn đang main thread và muốn update ngay, dùng value.(chỉ main thread)


    fun addPost(title: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = (_post.value?.maxOfOrNull { it.id } ?: 0) + 1
            val newPost = Post(id, title, body)
            repository.insertPosts(listOf(newPost))

            // Cập nhật lại danh sách
            val updatedList = _post.value.orEmpty() + newPost
            _post.postValue(updatedList) // an toàn trên background thread
        }
    }


    fun updatePost(post: Post, title: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedPost = post.copy(title = title, body = body)
            repository.insertPosts(listOf(updatedPost)) // REPLACE strategy
            fetchPosts()
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePost(post)
            fetchPosts()
        }
    }
}