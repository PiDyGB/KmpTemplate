package com.pidygb.mynasadailypics.core.database.di

import com.pidygb.mynasadailypics.core.common.di.includesCoreCommonModule
import com.pidygb.mynasadailypics.core.database.ComponentListAdapter
import com.pidygb.mynasadailypics.core.database.FooterAdapter
import com.pidygb.mynasadailypics.core.database.PageDatabase
import com.pidygb.mynasadailypics.core.database.PageEntity
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val pageDatabaseDriver: Module

private val pageDatabaseModule = module {
    single { ComponentListAdapter(get()) }
    single { FooterAdapter(get()) }
    single { 
        PageDatabase(
            driver = get(),
            PageEntityAdapter = PageEntity.Adapter(
                componentsAdapter = get<ComponentListAdapter>(),
                footerAdapter = get<FooterAdapter>()
            )
        )
    }
}


fun Module.includesCoreDatabaseModule() {
    includesCoreCommonModule()
    includes(pageDatabaseDriver, pageDatabaseModule)
}