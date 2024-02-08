package com.bijan.apis.product.dataSources

import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.network.NetworkDataSource
import com.bijan.libraries.core.network.TokenDatasource
import io.ktor.client.statement.HttpResponse

class CartRemoteDataSource(appConfig: AppConfig, tokenDatasource: TokenDatasource) :
    NetworkDataSource(appConfig.baseUrl, tokenDatasource) {
        suspend fun getCart(): HttpResponse {
            val  endPoint  = "/cart"
            return getHttpResponse(endPoint)
        }
}