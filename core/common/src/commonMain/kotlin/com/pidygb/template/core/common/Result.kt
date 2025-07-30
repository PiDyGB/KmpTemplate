package com.pidygb.template.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <reified T> Flow<*>.asResult(scope: CoroutineScope): StateFlow<Result<T>> = mapNotNull { value ->
    when (value) {
        is Result.Loading -> value
        is Result.Error -> value
        is T -> Result.Success(value)
        else -> null // Ignore any value that is not Loading, Error, or of type T
    }
}.onStart {
    emit(Result.Loading)
}.catch {
    emit(Result.Error(it))
}.stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = Result.Loading
)