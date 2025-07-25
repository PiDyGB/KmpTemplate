package com.pidygb.mynasadailypics.core.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pidygb.mynasadailypics.core.database.PicturesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val picturesDatabaseDriver: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(PicturesDatabase.Schema, get(), "pictures.db")
    }
}