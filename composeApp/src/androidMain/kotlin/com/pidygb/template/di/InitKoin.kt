package com.pidygb.template.di

import com.pidygb.template.feature.template.di.templateModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(templateModule)
    }
}