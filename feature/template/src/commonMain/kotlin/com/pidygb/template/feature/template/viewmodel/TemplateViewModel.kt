package com.pidygb.template.feature.template.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidygb.template.core.common.asResult
import com.pidygb.template.feature.template.data.repository.QuoteRepository
import kotlinx.coroutines.flow.flow

class TemplateViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    val result = flow {
        val quote = quoteRepository.getQuote()
        emit("\"${quote.text}\" - ${quote.author}")
    }.asResult<String>(viewModelScope)
}