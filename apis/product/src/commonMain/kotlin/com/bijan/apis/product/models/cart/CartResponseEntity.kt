package com.bijan.apis.product.models.cart

data class CartResponseEntity(
    val productId: Int = -1,
    val price: Double = 0.0,
    val discount: Int = 0,
    val amount: Double = 0.0,
    val quantity: Int = 0,
)