package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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


    fun getPostsLive(): LiveData<List<Post>> = postDao.getAllPosts().asLiveData()

    fun searchPosts(keyword: String): Flow<List<Post>> {
        val clean = keyword.trim()
        return if (clean.isBlank()) postDao.getAllPosts()
        else postDao.searchPosts(clean)
    }

    suspend fun insertPost(post: Post) = withContext(Dispatchers.IO) { postDao.insertPost(post) }
    suspend fun insertPosts(posts: List<Post>) = withContext(Dispatchers.IO) { postDao.insertPosts(posts) }
    suspend fun updatePost(post: Post) = withContext(Dispatchers.IO) { postDao.updatePost(post) }
    suspend fun deletePost(post: Post) = withContext(Dispatchers.IO) { postDao.deletePost(post) }

    suspend fun fetchPostsFromApi(): List<Post> = withContext(Dispatchers.IO) {
        if (!networkUtil.isNetworkAvailable()) return@withContext emptyList()
        return@withContext try {
            val posts = apiService.getPosts()
            postDao.insertPosts(posts)
            posts
        } catch (e: Exception) {
            emptyList()
        }
    }
}