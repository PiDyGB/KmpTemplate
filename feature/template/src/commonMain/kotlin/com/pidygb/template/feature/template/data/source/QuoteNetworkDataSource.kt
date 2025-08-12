package com.pidygb.template.feature.template.data.source

import com.pidygb.template.feature.template.data.model.QuoteNetworkModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface QuoteNetworkDataSource {
    suspend fun getRandomQuote(): QuoteNetworkModel
}

class KtorQuoteNetworkDataSource(
    private val httpClient: HttpClient
) : QuoteNetworkDataSource {

    override suspend fun getRandomQuote(): QuoteNetworkModel {
        return httpClient.get("https://dummyjson.com/quotes/random").body()
    }
}
