package com.pidygb.template.feature.template.domain.usecase

import com.pidygb.template.core.data.repository.TodoRepository
import com.pidygb.template.feature.template.data.repository.QuoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetRandomContentUseCase(
    private val quoteRepository: QuoteRepository,
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(): String = coroutineScope {
        val quoteDeferred = async { quoteRepository.getRandomQuote() }
        val todoDeferred = async { todoRepository.getRandomTodo() }

        val quote = quoteDeferred.await()
        val todo = todoDeferred.await()

        if (!todo.completed) {
            "TODO: ${todo.text} - User ID: ${todo.userId}"
        } else {
            "\"${quote.text}\" - ${quote.author}"
        }
    }
}
