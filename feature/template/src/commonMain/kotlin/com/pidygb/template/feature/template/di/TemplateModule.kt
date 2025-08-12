package com.pidygb.template.feature.template.di

import com.pidygb.template.core.data.di.includesCoreDataModule
import com.pidygb.template.feature.template.data.repository.DefaultQuoteRepository
import com.pidygb.template.feature.template.data.repository.QuoteRepository
import com.pidygb.template.feature.template.data.source.KtorQuoteNetworkDataSource
import com.pidygb.template.feature.template.data.source.QuoteNetworkDataSource
import com.pidygb.template.feature.template.domain.usecase.GetRandomContentUseCase
import com.pidygb.template.feature.template.viewmodel.TemplateViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val templateModule = module {
    includesCoreDataModule()

    factoryOf(::KtorQuoteNetworkDataSource) bind QuoteNetworkDataSource::class
    factoryOf(::DefaultQuoteRepository) bind QuoteRepository::class
    factoryOf(::GetRandomContentUseCase)

    viewModelOf(::TemplateViewModel)
}
