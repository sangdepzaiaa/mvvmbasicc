package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    @ColumnInfo("id")
    val id:Int,
    @ColumnInfo("title")
    val title:String,
    @ColumnInfo("boby")
    val boby: String
)