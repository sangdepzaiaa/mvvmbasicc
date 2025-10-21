package com.example.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post:Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Update
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<Post>>
}

//    @Query("SELECT * FROM posts WHERE title LIKE '%' || :keyword || '%' OR body LIKE '%' || :keyword || '%' ")
//     fun searchPosts(keyword: String): Flow<List<Post>>
