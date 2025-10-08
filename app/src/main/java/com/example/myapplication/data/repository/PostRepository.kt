package com.example.myapplication.data.repository

import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val networkUtil: NetworkUtil,
    private val apiService: ApiService,
    private val postDao: PostDao
){
    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
        if (networkUtil.isNetworkAvailable()) {
            try {
                val posts = apiService.getPosts()
                postDao.insertPosts(posts)
                posts
            } catch (e: Exception) {
                postDao.getPosts()
            }
        } else {
            postDao.getPosts()
        }
    }

    suspend fun insertPost(post: Post) = withContext(Dispatchers.IO) {
        postDao.insertPost(post)
    }

    suspend fun insertPosts(posts: List<Post>) = withContext(Dispatchers.IO) {
        postDao.insertPosts(posts)
    }

    suspend fun updatePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.deletePost(post)
    }
}