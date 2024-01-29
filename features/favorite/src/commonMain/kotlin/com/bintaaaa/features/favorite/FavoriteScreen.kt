package com.bintaaaa.features.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bijan.apis.product.LocalProductRepository
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.components.TopBarComponent
import com.example.libraries.components.utils.toRupiah
import com.seiko.imageloader.rememberImagePainter


@Composable
fun FavoriteScreen(onItemClick: (ProductResponseModel) -> Unit){
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
                    ProductItem(product = product) {
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

@Composable
fun ProductItem(product: ProductResponseModel, onItemClick: (ProductResponseModel) -> Unit) {
    val imagePainter = rememberImagePainter(product.image)
    Row(
        modifier = Modifier.padding(bottom = 6.dp).background(
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(12.dp)
        ).clickable {
            onItemClick.invoke(product)
        }.padding(6.dp).fillMaxWidth().height(100.dp)
    ) {
        Box(Modifier.width(70.dp).clip(RoundedCornerShape(8.dp))) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column {
            Text(
                text = product.name
            )
            Text(
                text = product.price.toRupiah
            )
        }
    }
}