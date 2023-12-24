package com.bijan.apis.product

import com.bijan.libraries.core.network.NetworkDataSource
import io.ktor.client.statement.HttpResponse

class ProductDataSource : NetworkDataSource("https://marketfake.fly.dev/") {

    suspend fun  getProductList(): HttpResponse{
        val endPoint = "product"

        return getHttpResponse(endPoint)
    }
}