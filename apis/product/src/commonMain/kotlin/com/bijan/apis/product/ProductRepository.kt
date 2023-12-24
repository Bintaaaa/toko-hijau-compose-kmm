package com.bijan.apis.product

import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.apis.product.models.ProductsResponseModel
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository {
    private val dataSource by lazy { ProductDataSource() }
    suspend fun getProducts(): Flow<List<ProductResponseModel>> {
        val data = dataSource.getProductList().body<ProductsResponseModel>().let { ProductMapper.mapResponseToList(it) }
        return flow {
            emit(
                data
            )
        }
    }
}