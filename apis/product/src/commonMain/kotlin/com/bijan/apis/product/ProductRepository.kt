package com.bijan.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.category.CategoriesResponse
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.repository.RepositoryReducer
import com.bijan.libraries.core.state.AsyncState
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val appConfig: AppConfig) : RepositoryReducer() {
    private val dataSources by lazy { ProductDataSources(appConfig) }

    suspend fun getProducts(query: String): Flow<AsyncState<List<ProductResponseModel>>> {

        return suspend {
            dataSources.getProductList(query)
        }.reduce<ProductsResponseModel, List<ProductResponseModel>>{ response ->
         if(response.data.isNullOrEmpty()){
                val throwable = Throwable("product is empty")
                AsyncState.Failure(throwable)
            }else{
            val data  = ProductMapper.mapResponseToList(response)
            AsyncState.Success(data)
        }
        }

    }

    suspend fun getCategories(): Flow<AsyncState<List<CategoryItemResponse>>>{
        return suspend { dataSources.getCategories() }.reduce<CategoriesResponse, List<CategoryItemResponse>> {response ->
            val responseData = response.data

            if(responseData.isNullOrEmpty()){
                val throwable = Throwable("Category is Empty")
                AsyncState.Failure(throwable)
            }else{
                val data = ProductMapper.mapResponseCategories(response).take(7)
                AsyncState.Success(data)
            }
        }
    }
}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("ProductRepository not provide") }