package com.bijan.apis.product

import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.apis.product.models.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(private val appConfig: AppConfig) {
    private val dataSources by lazy { ProductDataSources(appConfig) }
    suspend fun getProducts(): Flow<List<ProductResponseModel>> {
        val data = dataSources.getProductList().body<ProductsResponseModel>().let { ProductMapper.mapResponseToList(it) }
        return flow {
            emit(
                data
            )
        }
    }
}