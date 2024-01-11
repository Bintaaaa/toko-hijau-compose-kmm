package com.example.features.home

import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.state.Intent
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel<HomeState, HomeIntent>(HomeState()) {

     private fun getProductsLowPrice() = viewModelScope.launch {
        productRepository
            .getProducts(QUERY_LOW_PRICE)
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProductsLowPrice = it)
                }
            }
    }

    private fun updateName(name: String) = viewModelScope.launch {
        updateUiState {
            copy(name= name)
        }
    }

    private  fun getCategories() = viewModelScope.launch {
        productRepository.getCategories().stateIn(this).collectLatest {
            updateUiState {
                copy(
                    asyncCategories = it
                )
            }
        }
    }

    override fun sendIntent(intent: Intent) {
        when (intent){
            is HomeIntent.SetName ->{
                updateName(intent.name)
            }
            is HomeIntent.GetProductsLowPrice ->{
                getProductsLowPrice()
            }
            is HomeIntent.GetCategories ->{
                getCategories()
            }
        }
    }

    companion object{
        private  const val  QUERY_LOW_PRICE = "?sort=low_price"
    }
}