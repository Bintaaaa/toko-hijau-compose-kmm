package com.example.productdetail

import com.bijan.libraries.core.state.Intent

sealed class ProductDetailIntent(): Intent {
    data class GetProductDetail(val id: Int): ProductDetailIntent()
}