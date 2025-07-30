package com.pidygb.template.core.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pidygb.template.core.database.TemplateDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val templateDatabaseDriver: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(TemplateDatabase.Schema, "templates.db")
    }
}