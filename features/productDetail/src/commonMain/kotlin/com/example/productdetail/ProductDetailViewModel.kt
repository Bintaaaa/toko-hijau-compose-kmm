package com.example.productdetail

import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRespository: ProductRepository): ViewModel<ProductDetailState, ProductDetailIntent>(
    ProductDetailState()
) {

    private fun getProductDetail(id: Int) = viewModelScope.launch {
        productRespository.getProductDetail(id = id).stateIn(this).collectLatest {
            updateUiState {
                copy(productDetail = it)
            }
        }
    }

    private  fun getProductIsFavoriteById(productId: Int) = viewModelScope.launch {
        productRespository.isProductFavorite(productId).stateIn(this).collectLatest {
            updateUiState {
                copy(
                    isFavorite = it
                )
            }
        }

    }

    override fun sendIntent(intent: Intent) {
        when(intent){
            is ProductDetailIntent.GetProductDetail ->{
                val id = intent.id
                getProductDetail(id)
                getProductIsFavoriteById(id)
            }
        }
    }
}