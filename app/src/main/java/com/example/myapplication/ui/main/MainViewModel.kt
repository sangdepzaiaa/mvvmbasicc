package com.example.myapplication.ui.main

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository): ViewModel(){

    // LiveData trực tiếp từ Room → UI tự update
    val post: LiveData<List<Post>> = repository.getPostsLive()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _searchQuery = MutableStateFlow("") // lưu từ khóa
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.fetchPostsFromApi() // lần đầu lấy API vào Room
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            repository.insertPost(post)
            // Không cần fetch lại → LiveData tự notify
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            repository.updatePost(post)
            // LiveData tự notify
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
            // LiveData tự notify
        }
    }


    // Dòng dữ liệu realtime theo từ khóa
    val searchResults = searchQuery
        .debounce(300) // chờ người dùng gõ xong 300ms
        .flatMapLatest { keyword ->
            if (keyword.isBlank()) repository.searchPosts("") // hoặc repository.getAllPosts()
            else repository.searchPosts(keyword)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

}