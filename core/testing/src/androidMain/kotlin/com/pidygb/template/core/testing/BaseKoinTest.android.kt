package com.pidygb.template.core.testing

import androidx.test.platform.app.InstrumentationRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.logger.Level

actual fun KoinApplication.startModules() {
    androidLogger(Level.DEBUG)
    androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
}