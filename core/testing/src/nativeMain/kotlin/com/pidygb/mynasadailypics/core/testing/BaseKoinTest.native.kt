package com.pidygb.mynasadailypics.core.testing

import org.koin.core.KoinApplication
import org.koin.core.logger.Level

actual fun KoinApplication.startModules() {
    printLogger(Level.DEBUG)
}