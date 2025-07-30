package com.pidygb.template.feature.template.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidygb.template.core.common.Result
import com.pidygb.template.core.common.asResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TemplateViewModel : ViewModel() {

    val result = flow {
        emit("Parsing response...")
        delay(1_000)
        emit(Result.Loading)
        // Simulate some processing
        delay(5_000)
        emit("Hello, Template!")
    }.asResult<String>(viewModelScope)
}