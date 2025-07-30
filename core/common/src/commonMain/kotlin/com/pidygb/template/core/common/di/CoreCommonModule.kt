package com.pidygb.template.core.common.di

import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val coreCommonModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }
}

fun Module.includesCoreCommonModule() {
    includes(coreCommonModule)
}