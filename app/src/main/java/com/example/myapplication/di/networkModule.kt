package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.ui.main.MainViewModel

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// "https://jsonplaceholder.typicode.com/"

val networkModule = module{
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
         OkHttpClient.Builder()
            .readTimeout(30L,java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30L,java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(30L,java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    single{
         Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(get())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}