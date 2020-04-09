package com.example.shoppinglist

import android.app.Application
import com.example.shoppinglist.di.databaseModule
import com.example.shoppinglist.di.repositoryModule
import com.example.shoppinglist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule, databaseModule, repositoryModule))
        }
    }
}