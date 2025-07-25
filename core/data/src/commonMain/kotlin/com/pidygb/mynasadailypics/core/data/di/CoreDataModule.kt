package com.pidygb.mynasadailypics.core.data.di

import com.pidygb.mynasadailypics.core.data.PicturesRepository
import com.pidygb.mynasadailypics.core.data.PicturesRepositoryImpl
import com.pidygb.mynasadailypics.core.database.di.includesCoreDatabaseModule
import com.pidygb.mynasadailypics.core.network.di.includesCoreNetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module


val coreDataModule = module {
    includesCoreDatabaseModule()
    includesCoreNetworkModule()
    factory<PicturesRepository> {
        PicturesRepositoryImpl(
            database = get(),
            remoteDataSource = get(),
            context = Dispatchers.IO
        )
    }
}


fun Module.includesCoreDataModule() {
    includes(coreDataModule)
}

