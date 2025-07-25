package com.pidygb.mynasadailypics.core.database.di

import com.pidygb.mynasadailypics.core.database.PicturesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val picturesDatabaseDriver: Module

private val picturesDatabaseModule = module {
    single { PicturesDatabase(get()) }
}


fun Module.includesCoreDatabaseModule() {
    includes(picturesDatabaseDriver, picturesDatabaseModule)
}