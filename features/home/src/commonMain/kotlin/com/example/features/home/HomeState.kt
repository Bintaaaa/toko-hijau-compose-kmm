package com.example.features.home

import androidx.paging.PagingData
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.libraries.core.state.AsyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val name: String = "",
    val asyncProductsLowPrice: AsyncState<List<ProductResponseEntity>> = AsyncState.Default,
    val asyncCategories: AsyncState<List<CategoryItemResponse>> = AsyncState.Default,
    val pagingData: Flow<PagingData<ProductResponseEntity>> = emptyFlow(),
    val splash :  AsyncState<Boolean> =  AsyncState.Default
)