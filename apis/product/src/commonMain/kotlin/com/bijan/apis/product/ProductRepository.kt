package com.bijan.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.category.CategoriesResponse
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.apis.product.models.product.ProductDetailResponseModel
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.repository.RepositoryReducer
import com.bijan.libraries.core.state.AsyncState
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val appConfig: AppConfig) : RepositoryReducer() {
    private val productDataSources by lazy { ProductDataSources(appConfig) }
    private val favoriteLocalDataSource by lazy { ProductFavoriteLocalDataSource() }
    suspend fun getProducts(query: String): Flow<AsyncState<List<ProductResponseModel>>> {

        return suspend {
            productDataSources.getProductList(query)
        }.reduce<ProductsResponseModel, List<ProductResponseModel>> { response ->
            if (response.data.isNullOrEmpty()) {
                val throwable = Throwable("product is empty")
                AsyncState.Failure(throwable)
            } else {
                val data = ProductMapper.mapResponseToList(response)
                AsyncState.Success(data)
            }
        }

    }

    suspend fun getCategories(): Flow<AsyncState<List<CategoryItemResponse>>> {
        return suspend { productDataSources.getCategories() }.reduce<CategoriesResponse, List<CategoryItemResponse>> { response ->
            val responseData = response.data

            if (responseData.isNullOrEmpty()) {
                val throwable = Throwable("Category is Empty")
                AsyncState.Failure(throwable)
            } else {
                val data = ProductMapper.mapResponseCategories(response).take(3)
                AsyncState.Success(data)
            }
        }
    }

    suspend fun getProductDetail(id: Int): Flow<AsyncState<ProductDetailEntity>> {
        return suspend {
            productDataSources.getProductDetail(id = id)
        }.reduce<ProductDetailResponseModel, ProductDetailEntity> { response ->
            val responseData = response.data
            val data = ProductMapper.mapResponseProductDetail(responseData!!)
            AsyncState.Success(data)
        }
    }


    suspend fun getProductFavorites(): Flow<List<ProductResponseModel>>{
        return favoriteLocalDataSource.getProductFavorites()
    }

    suspend fun isProductFavorite(productId: Int): Flow<Boolean> {
        return favoriteLocalDataSource.getProductIsFavorite(productId)
    }

    suspend fun insertFavorite(productDetail: ProductDetailEntity){
        return favoriteLocalDataSource.insertData(productDetail)
    }

    suspend fun deleteFavorite(productId: Int){
        favoriteLocalDataSource.removeProduct(productId)
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("ProductRepository not provide") }