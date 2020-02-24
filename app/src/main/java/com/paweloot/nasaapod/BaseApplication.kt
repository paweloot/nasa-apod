package com.paweloot.nasaapod

import android.app.Application
import com.paweloot.nasaapod.di.appModule
import com.paweloot.nasaapod.di.networkModule
import com.paweloot.nasaapod.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, viewModelModule, networkModule))
        }
    }
}