package com.example.myapplication.data.repository


import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.network.ApiService
import com.example.myapplication.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val networkUtil: NetworkUtil
) {
    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
        if (networkUtil.isNetworkAvailable()) {
            try {
                val posts = apiService.getPosts()
                postDao.insertPosts(posts) // Lưu cache
                posts // return từ API
            } catch (e: Exception) {
                postDao.getPosts() // return cache khi API fail
            }
        } else {
            postDao.getPosts() // return cache khi không có mạng
        }
    }
}