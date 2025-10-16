package com.example.myapplication.ui.main

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    val posts: LiveData<List<Post>> = repository.allPosts.asLiveData()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addPost(post: Post) = viewModelScope.launch { repository.insertPost(post) }
    fun updatePost(post: Post) = viewModelScope.launch { repository.updatePost(post) }
    fun deletePost(post: Post) = viewModelScope.launch { repository.deletePost(post) }

    init {
        // Đảm bảo LiveData được observe trước khi fetch API
        viewModelScope.launch {
            val postsFromApi = repository.fetchPostsFromApi()
            if (postsFromApi.isEmpty()) {
                _error.postValue("Cannot fetch posts or API empty")
            }
        }
    }

}
