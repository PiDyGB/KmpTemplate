package com.pidygb.template.core.network.di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

private val httpClientModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(message, null, "HTTP Client")
                    }
                }
            }
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = "127.0.0.1"
                    port = 8080
                }
                contentType(ContentType.Application.Json)
            }
        }.also { Napier.base(DebugAntilog()) }
    }
}

fun Module.includesCoreNetworkModule() {
    includes(httpClientModule)
}