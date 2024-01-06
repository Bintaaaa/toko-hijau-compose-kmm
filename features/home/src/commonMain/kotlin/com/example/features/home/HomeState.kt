package com.example.features.home

import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.libraries.core.state.AsyncState

data class HomeState(
    val name: String = "",
    val asyncProductList: AsyncState<List<ProductResponseModel>> = AsyncState.Default
)