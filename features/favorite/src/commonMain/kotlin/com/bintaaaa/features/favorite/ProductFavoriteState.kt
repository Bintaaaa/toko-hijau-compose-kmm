package com.bintaaaa.features.favorite

import com.bijan.apis.product.models.product.ProductResponseModel

data class ProductFavoriteState(
    val productFavorites: List<ProductResponseModel> = emptyList()
)
