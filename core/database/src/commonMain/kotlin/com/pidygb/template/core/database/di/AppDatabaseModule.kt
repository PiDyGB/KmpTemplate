package com.pidygb.template.core.database.di

import com.pidygb.template.core.common.di.includesCoreCommonModule
import com.pidygb.template.core.database.TemplateDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val templateDatabaseDriver: Module

private val templateDatabaseModule = module {
    single {
        TemplateDatabase(driver = get())
    }
}


fun Module.includesCoreDatabaseModule() {
    includesCoreCommonModule()
    includes(templateDatabaseDriver, templateDatabaseModule)
}