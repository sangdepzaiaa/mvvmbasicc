package com.example.myapplication.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.PostDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module{

        val migration_2_3 = object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE posts ADD COLUMN content TEXT DEFAULT '' NOT NULL")
                }
        }


    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "db_post"
        )
        //.fallbackToDestructiveMigration() // Use this line if you want to reset the database on version changes
            .addMigrations(migration_2_3)
            .build()

    }

    single { get<AppDatabase>().postDao() }

}

