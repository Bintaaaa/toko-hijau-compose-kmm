package com.bijan.apis.product

import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.local.ProductRealm
import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.libraries.core.local.LocalDataSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductFavoriteLocalDataSource : LocalDataSources(ProductRealm::class) {
    suspend fun insertData(productDetailEntity: ProductDetailEntity) {
        val realm = ProductMapper.realMapFromDetail(productDetailEntity)
        insertObject(realm)
    }

    suspend fun removeProduct(productId: Int) {
        removeObject(ProductRealm::class, productId)
    }

    suspend fun getProductIsFavorite(productId: Int): Flow<Boolean> {
        return getObjectExistById(ProductRealm::class, productId)
    }

    suspend fun getProductFavorites(): Flow<List<ProductResponseModel>> {
        return getObjects(ProductRealm::class)
            .map {
                it.map { product ->
                    ProductMapper.realmMapToItem(product)
                }
            }
    }
}