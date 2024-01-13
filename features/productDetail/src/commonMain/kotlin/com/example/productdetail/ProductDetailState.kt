package com.example.productdetail

import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.libraries.core.state.AsyncState

data class ProductDetailState (
    val productDetail: AsyncState<ProductDetailEntity> = AsyncState.Default
)