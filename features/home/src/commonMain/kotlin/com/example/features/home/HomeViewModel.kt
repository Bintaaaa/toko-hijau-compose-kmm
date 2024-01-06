package com.example.features.home

import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel<HomeState>(HomeState()) {

     fun getProducts() = viewModelScope.launch {
        productRepository
            .getProducts()
            .stateIn(this)
            .collectLatest {
                updateUiState {
                    copy(asyncProductList = it)
                }
            }
    }

    fun updateName(name: String) = viewModelScope.launch {
        updateUiState {
            copy(name= name)
        }
    }
}