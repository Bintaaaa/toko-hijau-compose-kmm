package com.bijan.libraries.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class NetworkDataSource(private val baseUrl: String) {
    suspend fun getHttpResponse(endpoint: String): HttpResponse{
        val url = "$baseUrl$endpoint"
        return client.get(url){
            contentType(ContentType.Application.Json)
        }
    }
    companion object {
        private  val client: HttpClient by lazy{
            HttpClient {
                install(ContentNegotiation){
                    json(json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(Logging){
                    logger = Logger.SIMPLE
                    level = LogLevel.BODY

                }
            }
        }
    }
}