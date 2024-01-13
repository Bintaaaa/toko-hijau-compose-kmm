package com.bijan.apis.product

import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.network.NetworkDataSource
import io.ktor.client.statement.HttpResponse

class ProductDataSources(
    private val appConfig: AppConfig
) : NetworkDataSource(appConfig.baseUrl) {

    suspend fun getCategories(): HttpResponse{
        val endpoint = "product/category"
        return getHttpResponse(endpoint)
    }
    suspend fun  getProductList(query: String): HttpResponse{
        val endPoint = "product$query"

        return getHttpResponse(endPoint)
    }

    suspend fun getProductDetail(id: Int): HttpResponse{
        val endpoint = "product/$id"

        return getHttpResponse(endpoint)
    }
}
