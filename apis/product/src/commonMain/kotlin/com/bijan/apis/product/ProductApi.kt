package com.bijan.apis.product

import com.bijan.apis.product.models.ProductResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductApi {
    fun getProducts(): Flow<List<ProductResponseModel>> {
        return flow {
            emit(
                listOf(
                    ProductResponseModel(
                        id = 1,
                        name = "Baju Barong",
                        price = 300000.0
                    )
                )
            )
        }
    }
}