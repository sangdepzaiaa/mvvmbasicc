package com.example.myapplication.data.repository

import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val networkUtil: NetworkUtil,
    private val apiService: ApiService,
    private val post: PostDao
){
    suspend fun getPosts() = withContext(Dispatchers.IO){
        if (networkUtil.isNetworkAvailable()){
            try {
                val posts = apiService.getPosts()
                post.insertPost(posts)
                return@withContext posts
            }catch (e : Exception){
                return@withContext  post.getList()
            }
        }else{
            return@withContext post.getList()
        }
    }
}