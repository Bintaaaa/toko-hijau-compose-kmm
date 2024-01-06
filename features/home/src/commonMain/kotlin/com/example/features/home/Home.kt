package com.example.features.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.bijan.apis.product.ProductRepository
import com.bijan.apis.product.models.ProductResponseModel
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.state.AsyncState
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

    val homeState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        homeViewModel.sendIntent(
            HomeIntent.SetName("Bijan")
        )
        homeViewModel.sendIntent(
            HomeIntent.GetProducts
        )
    }

    LazyColumn {
        item {
            Text(
                text = homeState.name,
                fontWeight = FontWeight.Bold
            )
        }
        when(val productList = homeState.asyncProductList){
            is AsyncState.Loading ->{
                item {
                    CircularProgressIndicator(
                    )
                }
            }
            is AsyncState.Failure -> {
                item {
                    Text(text = productList.throwable.message.orEmpty())
                }
            }
            is AsyncState.Success -> {
                items(productList.data){
                    ProductItem(it)
                }
            }
            else -> {}
        }

    }
}


@Composable
fun ProductItem(products: ProductResponseModel){
    Text(
        text = products.name
    )
}