package com.pidygb.mynasadailypics.core.network

import com.pidygb.mynasadailypics.core.model.Page

interface PageRemoteDataSource {
    suspend fun getPage(): Page
}