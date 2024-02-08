package com.bintaaaa.features.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.apis.product.repository.LocalProductRepository
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.components.ProductItemComponent
import com.example.libraries.components.components.TopBarComponent


@Composable
fun FavoriteScreen(onItemClick: (ProductResponseEntity) -> Unit){
    val productRepository = LocalProductRepository.current
    val viewModel = rememberViewModel { ProductFavoritesViewModel(productRepository) }
    val productFavoriteState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.sendIntent(ProductFavoriteIntent.GetFavorites)
    }

    Scaffold(topBar = { TopBarComponent("Favorite") }) {
        Box(modifier =  Modifier.padding(12.dp)) {
            LazyColumn {
                items(productFavoriteState.productFavorites) { product ->
                    ProductItemComponent(product = product) {
                        onItemClick.invoke(it)
                    }
                }
                if (productFavoriteState.productFavorites.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Product not found!")
                        }
                    }
                }
            }
        }
    }
}