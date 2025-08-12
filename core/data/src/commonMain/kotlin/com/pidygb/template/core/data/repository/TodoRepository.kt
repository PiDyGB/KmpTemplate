package com.pidygb.template.core.data.repository

import com.pidygb.template.core.model.Todo
import com.pidygb.template.core.network.TodoNetworkDataSource

interface TodoRepository {
    suspend fun getRandomTodo(): Todo
}

class DefaultTodoRepository(
    private val networkDataSource: TodoNetworkDataSource
) : TodoRepository {

    override suspend fun getRandomTodo(): Todo {
        val networkTodo = networkDataSource.getRandomTodo()
        return Todo(
            id = networkTodo.id,
            text = networkTodo.todo,
            completed = networkTodo.completed,
            userId = networkTodo.userId
        )
    }
}
