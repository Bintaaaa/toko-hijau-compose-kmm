package com.example.features.home

import com.bijan.apis.product.ProductApi
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.libraries.core.viewModel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val productApi: ProductApi) : ViewModel() {
    val produts = MutableStateFlow<List<ProductResponseModel>>(emptyList())


    fun getProducts() = viewModelScope.launch {
        productApi
            .getProducts()
            .stateIn(this)
            .collect(produts)
    }
}