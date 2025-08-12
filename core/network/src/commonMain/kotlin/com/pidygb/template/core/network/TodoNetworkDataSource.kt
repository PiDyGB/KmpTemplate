package com.pidygb.template.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface TodoNetworkDataSource {
    suspend fun getRandomTodo(): TodoNetworkModel
}

class KtorTodoNetworkDataSource(
    private val httpClient: HttpClient
) : TodoNetworkDataSource {

    override suspend fun getRandomTodo(): TodoNetworkModel {
        return httpClient.get("https://dummyjson.com/todos/random").body()
    }
}
