package com.example.features.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bijan.apis.product.dataSources.ProductRemoteDataSources
import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.libraries.core.AppConfig
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay

class HomePaggingSources(
    appConfig: AppConfig,
    private val query: String
) : PagingSource<Int, ProductResponseEntity>() {

    private val dataSources by lazy { ProductRemoteDataSources(appConfig) }

    override fun getRefreshKey(state: PagingState<Int, ProductResponseEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponseEntity> {
        val page = params.key ?: 1
        val queryPage = if (query.isNotEmpty()) {
            "$query&page=$page"
        } else {
            "?page=$page"
        }
        return try {
            val dataResponse = dataSources
                .getProductList(queryPage)

            val data = dataResponse
                .body<ProductsResponseModel>()
                .data
                ?.filterNotNull()
                .orEmpty()
                .map {
                    ProductMapper.mapItemResponseToItemList(it)
                }
            delay(2000)

            when {
                dataResponse.status.isSuccess() -> {
                    val nextKey = if (data.isNotEmpty()) page + 1 else null
                    val prevKey = (page - 1).takeIf { it >= 1 }
                    LoadResult.Page(
                        data = data,
                        nextKey = nextKey,
                        prevKey = prevKey
                    )
                }
                else -> {
                    val throwable = Throwable(dataResponse.bodyAsText())
                    LoadResult.Error(throwable)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}