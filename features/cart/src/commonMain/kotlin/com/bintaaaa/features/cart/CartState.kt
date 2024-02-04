package com.bintaaaa.features.cart

import com.bijan.apis.product.models.cart.CartItemEntity
import com.bijan.libraries.core.state.AsyncState

data class CartState(
    val cartState: AsyncState<List<CartItemEntity?>> = AsyncState.Default
)