package com.pidygb.template.core.data.di

import com.pidygb.template.core.database.di.includesCoreDatabaseModule
import com.pidygb.template.core.network.di.includesCoreNetworkModule
import org.koin.core.module.Module
import org.koin.dsl.module


val coreDataModule = module {
    includesCoreDatabaseModule()
    includesCoreNetworkModule()
}


fun Module.includesCoreDataModule() {
    includes(coreDataModule)
}

