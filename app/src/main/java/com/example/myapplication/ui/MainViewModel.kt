package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
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
                _post.value = repository.getPosts()

            }catch (e : Exception){
                _error.value = "error"
                _post.value = repository.getPosts()
            }
        }
    }
}