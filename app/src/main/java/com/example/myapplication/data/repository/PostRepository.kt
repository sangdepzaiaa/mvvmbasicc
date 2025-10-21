package com.example.myapplication.data.repository

import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetWorkUtil
import kotlinx.coroutines.flow.Flow

class PostRepository(
    val apiService: ApiService,
    val postDao: PostDao,
    val networkUtil: NetWorkUtil
){
    val allPosts:Flow<List<Post>> = postDao.getAllPosts()
    suspend fun insertPost(post: Post) = postDao.insertPost(post)
    suspend fun inserposts(posts: List<Post>) = postDao.insertPosts(posts)
    suspend fun deletePost(post:Post) = postDao.deletePost(post)
    suspend fun updatePost(post: Post) = postDao.updatePost(post)

    suspend fun fetchPostsFromApi():List<Post> {
        if(!networkUtil.isNetworkAvailable()) return  emptyList()
        return try{
            val posts = apiService.getPosts()
            if (posts.isEmpty()){
                postDao.insertPosts(posts)
            }
            return posts
        }catch (e : Exception){
            emptyList()
        }
    }
}