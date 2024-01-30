package com.bintaaaa.apis.authentication

import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.network.NetworkDataSource
import com.bintaaaa.apis.authentication.models.LoginBody
import io.ktor.client.statement.HttpResponse

class AuthenticationRemoteDataSource(appConfig: AppConfig): NetworkDataSource(appConfig.baseUrl) {
    suspend fun postLogin(name: String, password: String): HttpResponse{
        val loginRequest = LoginBody(name,password)
        return  postHttpResponse(endpoint = "/auth/login", body = loginRequest)
    }
}