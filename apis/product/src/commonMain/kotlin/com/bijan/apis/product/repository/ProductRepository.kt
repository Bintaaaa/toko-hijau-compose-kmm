package com.bijan.apis.product.repository

import androidx.compose.runtime.compositionLocalOf
import com.bijan.apis.product.dataSources.CartRemoteDataSource
import com.bijan.apis.product.dataSources.ProductFavoriteLocalDataSource
import com.bijan.apis.product.dataSources.ProductRemoteDataSources
import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.cart.CartResponseEntity
import com.bijan.apis.product.models.cart.CartResponseModel
import com.bijan.apis.product.models.category.CategoriesResponse
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.apis.product.models.product.ProductDetailResponseModel
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.network.TokenDatasource
import com.bijan.libraries.core.repository.RepositoryReducer
import com.bijan.libraries.core.state.AsyncState
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val appConfig: AppConfig, private val tokenDatasource: TokenDatasource) :
    RepositoryReducer() {
    private val productRemoteDataSources by lazy { ProductRemoteDataSources(appConfig) }
    private val favoriteLocalDataSource by lazy { ProductFavoriteLocalDataSource() }
    private val cartRemoteDataSource by lazy { CartRemoteDataSource(appConfig, tokenDatasource) }
    suspend fun getProducts(query: String): Flow<AsyncState<List<ProductResponseEntity>>> {

        return suspend {
            productRemoteDataSources.getProductList(query)
        }.reduce<ProductsResponseModel, List<ProductResponseEntity>> { response ->
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
        return suspend { productRemoteDataSources.getCategories() }.reduce<CategoriesResponse, List<CategoryItemResponse>> { response ->
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
            productRemoteDataSources.getProductDetail(id = id)
        }.reduce<ProductDetailResponseModel, ProductDetailEntity> { response ->
            val responseData = response.data
            val data = ProductMapper.mapResponseProductDetail(responseData!!)
            AsyncState.Success(data)
        }
    }


    suspend fun getProductFavorites(): Flow<List<ProductResponseEntity>> {
        return favoriteLocalDataSource.getProductFavorites()
    }

    suspend fun isProductFavorite(productId: Int): Flow<Boolean> {
        return favoriteLocalDataSource.getProductIsFavorite(productId)
    }

    suspend fun insertFavorite(productDetail: ProductDetailEntity) {
        return favoriteLocalDataSource.insertData(productDetail)
    }

    suspend fun deleteFavorite(productId: Int) {
        favoriteLocalDataSource.removeProduct(productId)
    }

    suspend fun getCart(): Flow<AsyncState<List<CartResponseEntity>>> {
        return suspend {
            cartRemoteDataSource.getCart()
        }.reduce<CartResponseModel, List<CartResponseEntity>> { response ->
            val responseData = response.data.orEmpty()

            if (responseData.isEmpty()) {
                AsyncState.Failure(Throwable("Cart is empty"))
            } else {
                AsyncState.Success(ProductMapper.mapResponseToCart(response.data?.filterNotNull()))
            }
        }
    }

}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("ProductRepository not provide") }