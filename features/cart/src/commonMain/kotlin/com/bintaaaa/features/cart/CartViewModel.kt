package com.bintaaaa.features.cart

import com.bijan.apis.product.models.ProductMapper
import com.bijan.apis.product.repository.ProductRepository
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(val cartRepository: ProductRepository) : ViewModel<CartState, CartIntent>(CartState()) {
    override fun sendIntent(intent: Intent) {
        when (intent) {
            is CartIntent.GetCart -> {
                getCart()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCart() = viewModelScope.launch {
        cartRepository.getCart()
            .flatMapMerge {
                when (it) {
                    AsyncState.Loading -> {
                        flowOf(AsyncState.Loading)
                    }

                    is AsyncState.Success -> {
                        val data = it.data.map { cart ->
                            val result = when (val asyncProductDetail =
                                cartRepository.getProductDetail(cart.productId).last()) {
                                is AsyncState.Success -> {
                                    ProductMapper.mapCartToCartProduct(cart, asyncProductDetail.data)
                                }

                                else -> null
                            }
                            return@map result
                        }
                        flowOf(AsyncState.Success(data))
                    }

                    is AsyncState.Failure -> {
                        flowOf(AsyncState.Failure(it.throwable))
                    }

                    else -> emptyFlow()
                }
            }
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(cartState = it)
                }
            }
        }
}