package com.example.myapplication.data.repository


import com.example.myapplication.data.local.PostDao
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(
    private val apiService: ApiService,
    private val networkUtil: NetworkUtil,
    private val postDao: PostDao
){
    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO){
        try {
            if (networkUtil.isNetworkAvailable()){
                val post = apiService.getPosts()
                postDao.insertPost(post)
                return@withContext post
            }else{
                return@withContext  postDao.getPosts()
            }
        }catch (e : Exception){
            return@withContext  postDao.getPosts()
        }
    }
}





















//class PostRepository(
//    private val apiService: ApiService,
//    private val postDao: PostDao,
//    private val networkUtil: NetworkUtil
//) {
//    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
//        if (networkUtil.isNetworkAvailable()) {
//            try {
//                val posts = apiService.getPosts()
//                postDao.insertPosts(posts) // Lưu cache
//                return@withContext posts // return từ API
//            } catch (e: Exception) {
//                return@withContext postDao.getPosts() // return cache khi API fail
//            }
//        } else {
//           return@withContext postDao.getPosts() // return cache khi không có mạng
//        }
//    }
//}

