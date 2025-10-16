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
    val allPosts: Flow<List<Post>> = postDao.getAllPosts()
    suspend fun insertPost(post: Post) =  postDao.insertPost(post)
    suspend fun insertPosts(posts: List<Post>) =  postDao.insertPosts(posts)
    suspend fun updatePost(post: Post) =  postDao.updatePost(post)
    suspend fun deletePost(post: Post) =  postDao.deletePost(post)

    suspend fun fetchPostsFromApi(): List<Post> {
        if (!networkUtil.isNetworkAvailable()) return emptyList()
        return try {
            val posts = apiService.getPosts()
            if (posts.isNotEmpty()) {
                // insert mà không cần withContext, Room tự chạy IO
                postDao.insertPosts(posts)
            }
            posts
        } catch (e: Exception) {
            emptyList()
        }
    }
}