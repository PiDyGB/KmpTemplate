package com.pidygb.template.feature.template.di

import com.pidygb.template.core.data.di.includesCoreDataModule
import com.pidygb.template.feature.template.viewmodel.TemplateViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val templateModule = module {
    includesCoreDataModule()
    viewModelOf(::TemplateViewModel)
}

