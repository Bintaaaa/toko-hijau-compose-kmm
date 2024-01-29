package com.example.productdetail

import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.libraries.core.state.Intent

sealed class ProductDetailIntent(): Intent {
    data class GetProductDetail(val id: Int): ProductDetailIntent()

    data class ToggleFavorite(val productDetailEntity: ProductDetailEntity) : ProductDetailIntent()
}