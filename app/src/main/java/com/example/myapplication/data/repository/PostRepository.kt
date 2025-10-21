package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetWorkUtil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PostRepository(
   private val postDao: PostDao,
   private val apiService: ApiService,
   private val netWorkUtil: NetWorkUtil
){
   val allPost: Flow<List<Post>> = postDao.getALlPost()
    suspend fun insertPost(post: Post) = postDao.insertPost(post)
    suspend fun insertPosts(posts: List<Post>) = postDao.insertPosts(posts)
    suspend fun deletePost(post: Post) = postDao.deletePost(post)
    suspend fun updatePost(post: Post) = postDao.updatePost(post)
    suspend fun fetchAllPost(): List<Post> {
        if (!netWorkUtil.isNetwordAvailable()) return emptyList()
        return try {
            val posts = apiService.getPosts()
            if (posts.isNotEmpty()){
                postDao.insertPosts(posts)
            }
            return posts
        } catch (e: Exception) {
            emptyList()
        }
    }
}