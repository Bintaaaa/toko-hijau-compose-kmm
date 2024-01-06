package com.bijan.apis.product

import androidx.compose.runtime.compositionLocalOf
import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.apis.product.models.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import com.bijan.libraries.core.repository.RepositoryReducer
import com.bijan.libraries.core.state.AsyncState
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class ProductRepository(private val appConfig: AppConfig) : RepositoryReducer() {
    private val dataSources by lazy { ProductDataSources(appConfig) }
    suspend fun getProducts(): Flow<AsyncState<List<ProductResponseModel>>> {
        val data = dataSources.getProductList().body<ProductsResponseModel>().let { ProductMapper.mapResponseToList(it) }

        return suspend {
            dataSources.getProductList()
        }.reduce<ProductsResponseModel, List<ProductResponseModel>>{response ->
         if(response.data.isNullOrEmpty()){
                val throwable = Throwable("product is empty")
                AsyncState.Failure(throwable)
            }else{
                ProductMapper.mapResponseToList(response)
                AsyncState.Success(data)
            }
        }.onStart {
            AsyncState.Loading
        }

    }
}

val LocalProductRepository = compositionLocalOf<ProductRepository> { error("ProductRepository not provide") }