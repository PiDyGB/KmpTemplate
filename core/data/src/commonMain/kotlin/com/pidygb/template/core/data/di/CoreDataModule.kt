package com.pidygb.template.core.data.di

import com.pidygb.template.core.data.repository.DefaultTodoRepository
import com.pidygb.template.core.data.repository.TodoRepository
import com.pidygb.template.core.database.di.includesCoreDatabaseModule
import com.pidygb.template.core.network.di.includesCoreNetworkModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val coreDataModule = module {
    includesCoreDatabaseModule()
    includesCoreNetworkModule()

    factoryOf(::DefaultTodoRepository) bind TodoRepository::class
}

fun Module.includesCoreDataModule() {
    includes(coreDataModule)
}