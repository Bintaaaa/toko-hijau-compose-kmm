package com.example.features.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.bijan.apis.product.ProductRepository
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.viewModel.rememberViewModel


@Composable
fun Home(){
    val appConfig = LocalAppConfig.current

    val productRepository = remember {
        ProductRepository(appConfig)
    }
    val homeViewModel = rememberViewModel {
        HomeViewModel(productRepository)
    }

    val products by homeViewModel.produts.collectAsState()

    LaunchedEffect(Unit){
        homeViewModel.getProducts()
    }

    LazyColumn {
        items(products){
            ProductItem(it)
        }
    }
}


@Composable
fun ProductItem(products: ProductResponseModel){
    Text(
        text = products.name
    )
}