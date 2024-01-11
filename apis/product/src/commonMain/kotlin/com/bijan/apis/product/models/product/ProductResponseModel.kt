package com.bijan.apis.product.models.product

data class ProductResponseModel (
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
)