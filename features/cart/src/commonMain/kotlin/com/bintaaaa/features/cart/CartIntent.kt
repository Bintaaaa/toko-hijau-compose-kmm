package com.bintaaaa.features.cart

import com.bijan.libraries.core.state.Intent

sealed class CartIntent: Intent {
    data object GetCart : CartIntent()
}