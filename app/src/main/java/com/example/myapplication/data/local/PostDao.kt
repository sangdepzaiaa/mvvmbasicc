package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.model.Post

@Dao
interface PostDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: List<Post>)

    @Query("SELECT * FROM posts")
    suspend fun getList() : List<Post>
}