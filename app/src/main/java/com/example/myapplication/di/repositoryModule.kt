package com.example.myapplication.di

import com.example.myapplication.data.repository.PostRepository
import org.koin.dsl.module

val repositoryModule = module{
    single {
        PostRepository(get(),get(),get())
    }
}