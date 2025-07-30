package com.pidygb.template.core.testing

import org.koin.core.KoinApplication
import org.koin.core.logger.Level

actual fun KoinApplication.startModules() {
    printLogger(Level.DEBUG)
}