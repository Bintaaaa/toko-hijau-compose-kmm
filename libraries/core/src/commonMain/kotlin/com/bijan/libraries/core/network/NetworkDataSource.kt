package com.bijan.libraries.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class NetworkDataSource(private val baseUrl: String,  private val tokenDataSources: TokenDatasource = TokenDatasource.Default) {

    suspend fun postHttpResponse(endpoint: String, body: Any): HttpResponse{
        val url = "$baseUrl$endpoint"
        return client.post(url){

            contentType(ContentType.Application.Json,)
            setBody(body)
        }
    }
    suspend fun getHttpResponse(endpoint: String): HttpResponse{
        val url = "$baseUrl$endpoint"
        return client.get(url){
            headers.append(
                name = "Authorization",
                value = "Bearer ${tokenDataSources.getToken}"
            )
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