package com.clivewatts.pawpawscroll

import android.app.Application
import com.clivewatts.pawpawscroll.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PawPawScrollApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@PawPawScrollApplication)
            modules(appModule)
        }
    }
}