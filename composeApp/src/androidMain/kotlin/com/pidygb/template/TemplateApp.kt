package com.pidygb.template

import android.app.Application
import com.pidygb.template.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class TemplateApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TemplateApp)
        }

    }
}