package com.pidygb.template.feature.template.data.repository

import com.pidygb.template.feature.template.data.source.QuoteNetworkDataSource
import com.pidygb.template.feature.template.domain.model.Quote

interface QuoteRepository {
    suspend fun getQuote(): Quote
}

class DefaultQuoteRepository(
    private val networkDataSource: QuoteNetworkDataSource
) : QuoteRepository {

    override suspend fun getQuote(): Quote {
        val networkQuote = networkDataSource.getQuote()
        return Quote(
            id = networkQuote.id,
            text = networkQuote.quote,
            author = networkQuote.author
        )
    }
}
