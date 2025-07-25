package com.pidygb.mynasadailypics.core.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pidygb.mynasadailypics.core.database.PageDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val pageDatabaseDriver: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(PageDatabase.Schema, get(), "pages.db")
    }
}