package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.Post

@Database(entities = [Post::class], version = 4)
abstract class AppDatabase : RoomDatabase(){
    abstract fun postDao(): PostDao
}