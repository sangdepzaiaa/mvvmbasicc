package com.example.myapplication.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Thêm cột mới với giá trị mặc định,dữ liệu cũ vẫn còn
            database.execSQL("ALTER TABLE posts ADD COLUMN content TEXT DEFAULT '' NOT NULL")
        }
    }

    // Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "db_database"
        )
            //.fallbackToDestructiveMigration()  //tự động xoá db nếu version tăng
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    // Dao
    single { get<AppDatabase>().postDao() }
}
