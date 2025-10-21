package com.example.myapplication.data

import android.app.Application
import com.example.myapplication.di.databaseModule
import com.example.myapplication.di.networkModule
import com.example.myapplication.di.repositoryModule
import com.example.myapplication.di.utilModule
import com.example.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    utilModule,
                    viewModelModule
                )
            )
        }
    }
}