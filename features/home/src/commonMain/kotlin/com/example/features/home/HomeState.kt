package com.example.features.home

import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.libraries.core.state.AsyncState

data class HomeState(
    val name: String = "",
    val asyncProductsLowPrice: AsyncState<List<ProductResponseModel>> = AsyncState.Default,
    val asyncCategories: AsyncState<List<CategoryItemResponse>> = AsyncState.Default
)