package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "db_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Dao
    single { get<AppDatabase>().postDao() }
}
