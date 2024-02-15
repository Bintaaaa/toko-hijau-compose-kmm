package com.bintaaaa.features.favorite

import com.bijan.apis.product.repository.ProductRepository
import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductFavoritesViewModel(private val productRepository: ProductRepository): ViewModel<ProductFavoriteState, ProductFavoriteIntent>(ProductFavoriteState()) {

    private fun getProductFavorites() = viewModelScope.launch {
        productRepository.getProductFavorites().stateIn(this).collectLatest {
            updateUiState {
                copy(
                    productFavorites = it
                )
            }
        }
    }

    override fun sendIntent(intent: Intent) {
        when(intent){
            is ProductFavoriteIntent.GetFavorites ->{
                    getProductFavorites()
            }
        }
    }
}