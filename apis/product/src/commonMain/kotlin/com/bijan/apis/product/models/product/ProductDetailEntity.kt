package com.bijan.apis.product.models.product

data class ProductDetailEntity(
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val userReview: List<UserReview>,
    val images: List<String>,
)

data class UserReview (
    val user: String,
    val review: String
)