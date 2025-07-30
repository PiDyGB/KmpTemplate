package com.pidygb.template.core.database.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pidygb.template.core.database.TemplateDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val templateDatabaseDriver: Module = module {
    single {
        AndroidSqliteDriver(TemplateDatabase.Schema, get(), "templates.db")
    }
}