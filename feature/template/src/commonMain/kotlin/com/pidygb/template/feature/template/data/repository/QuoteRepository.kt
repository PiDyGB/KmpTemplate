package com.pidygb.template.feature.template.data.repository

import com.pidygb.template.feature.template.data.source.QuoteNetworkDataSource
import com.pidygb.template.feature.template.domain.model.Quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote
}

class DefaultQuoteRepository(
    private val networkDataSource: QuoteNetworkDataSource
) : QuoteRepository {

    override suspend fun getRandomQuote(): Quote {
        val networkQuote = networkDataSource.getRandomQuote()
        return Quote(
            id = networkQuote.id,
            text = networkQuote.quote,
            author = networkQuote.author
        )
    }
}
