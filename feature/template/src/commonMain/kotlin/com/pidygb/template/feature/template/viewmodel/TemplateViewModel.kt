package com.pidygb.template.feature.template.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pidygb.template.core.common.asResult
import com.pidygb.template.feature.template.domain.usecase.GetRandomContentUseCase
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TemplateViewModel(
    private val getRandomContentUseCase: GetRandomContentUseCase
) : ViewModel() {

    val result = flow {
        val content = getRandomContentUseCase()
        emit(content)
    }.asResult<String>(viewModelScope)
}

fun get() = object  : KoinComponent {
    val viewModel: TemplateViewModel by inject()
}.viewModel
