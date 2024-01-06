package com.bijan.apis.product

import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.network.NetworkDataSource
import io.ktor.client.statement.HttpResponse

class ProductDataSources(
    private val appConfig: AppConfig
) : NetworkDataSource(appConfig.baseUrl) {

    suspend fun  getProductList(): HttpResponse{
        val endPoint = "product"

        return getHttpResponse(endPoint)
    }
}
