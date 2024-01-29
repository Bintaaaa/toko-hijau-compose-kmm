package com.bintaaaa.features.favorite

import com.bijan.libraries.core.state.Intent

sealed class ProductFavoriteIntent: Intent {
    data object GetFavorites: ProductFavoriteIntent()
}