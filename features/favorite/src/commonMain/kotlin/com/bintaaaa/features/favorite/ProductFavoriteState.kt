package com.bintaaaa.features.favorite

import com.bijan.apis.product.models.product.ProductResponseEntity

data class ProductFavoriteState(
    val productFavorites: List<ProductResponseEntity> = emptyList()
)
