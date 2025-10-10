package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PostRepository(
    private val networkUtil: NetworkUtil,
    private val apiService: ApiService,
    private val postDao: PostDao
){


    // LiveData trực tiếp từ Room
    fun getPostsLive(): LiveData<List<Post>> = postDao.getPosts()

    suspend fun insertPost(post: Post) = withContext(Dispatchers.IO) {
        postDao.insertPost(post)
    }

    suspend fun updatePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.deletePost(post)
    }

    fun searchPosts(keyword: String): Flow<List<Post>> =postDao.searchPosts(keyword)

    // Tuỳ chọn: fetch API + cache vào Room lần đầu
    suspend fun fetchPostsFromApi() {
        if (networkUtil.isNetworkAvailable()) {
            try {
                val posts = apiService.getPosts()
                postDao.insertPosts(posts)
            } catch (e: Exception) {
                // API lỗi → giữ cache Room
            }
        }
    }
}