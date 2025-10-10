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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Search Flow realtime kiểu YouTube
    val searchResults: StateFlow<List<Post>> = _searchQuery
        .debounce(300)
        .flatMapLatest { repository.searchPosts(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun onSearchChanged(query: String) {
        _searchQuery.value = query
    }

    fun addPost(post: Post) = viewModelScope.launch { repository.insertPost(post) }
    fun updatePost(post: Post) = viewModelScope.launch { repository.updatePost(post) }
    fun deletePost(post: Post) = viewModelScope.launch { repository.deletePost(post) }

    init {
        // Fetch API lần đầu, nếu fail → cập nhật error
        viewModelScope.launch {
            try {
                repository.fetchPostsFromApi()
            } catch (e: Exception) {
                _error.postValue("Cannot fetch posts: ${e.message}")
            }
        }
    }
}
