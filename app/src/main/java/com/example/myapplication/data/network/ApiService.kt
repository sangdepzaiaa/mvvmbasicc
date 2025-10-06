package com.example.myapplication.data.network

import com.example.myapplication.data.model.Post
import retrofit2.http.GET

interface ApiService{
    @GET("posts")
    suspend fun getPosts(): List<Post>


}