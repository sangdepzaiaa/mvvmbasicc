package com.example.myapplication.di


import com.example.myapplication.util.NetWorkUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module{
    single {
        NetWorkUtil(androidContext())
    }
}
