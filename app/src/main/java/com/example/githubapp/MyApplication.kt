package com.example.githubapp

import android.app.Application
import com.example.di.koinModule
import com.example.githubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(koinModule, viewModelModule)
        }
    }
}