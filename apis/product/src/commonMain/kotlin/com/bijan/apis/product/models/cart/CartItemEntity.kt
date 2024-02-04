package com.bijan.apis.product.models.cart

import com.bijan.apis.product.models.product.ProductDetailEntity

data class CartItemEntity (
    val amount: Double,
    val discount: Int,
    val price: Double,
    val productId: Int,
    val quantity: Int,
    val productDetail: ProductDetailEntity
)