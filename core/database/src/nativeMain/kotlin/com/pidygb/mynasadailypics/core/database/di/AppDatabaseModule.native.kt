package com.pidygb.mynasadailypics.core.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pidygb.mynasadailypics.core.database.PageDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val pageDatabaseDriver: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(PageDatabase.Schema, "pages.db")
    }
}