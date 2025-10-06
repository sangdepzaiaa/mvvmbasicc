package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.util.NetworkUtil
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{
    single{
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    }
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "db_database"
        ).fallbackToDestructiveMigration().build()
    }
    single {
        NetworkUtil(androidContext())
    }
    single {
        get<AppDatabase>().postDao()
    }
    single {
        PostRepository(get(), get(), get())
    }
    viewModel { MainViewModel(get()) }
}