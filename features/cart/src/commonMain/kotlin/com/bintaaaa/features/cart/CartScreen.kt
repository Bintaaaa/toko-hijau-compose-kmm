package com.bintaaaa.features.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import com.bijan.apis.product.models.cart.CartItemEntity
import com.bijan.apis.product.repository.LocalProductRepository
import com.bijan.libraries.core.repository.UnauthorizedException
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.components.TopBarComponent
import com.example.libraries.components.utils.toRupiah
import com.seiko.imageloader.rememberImagePainter

@Composable
fun CartScreen(loginAction: () -> Unit, onCart: () -> Unit, onBack: () -> Unit ) {
    val productRepository = LocalProductRepository.current
    val cartViewModel = rememberViewModel { CartViewModel(cartRepository = productRepository) }
    val state by cartViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        cartViewModel.sendIntent(CartIntent.GetCart)
    }

    Scaffold(
        topBar = { TopBarComponent("Cart", onBack = onBack ) }
    ) {
        when (val data = state.cartState) {
            is AsyncState.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncState.Success -> {
                val cart = data.data
                LazyColumn {
                    items(cart) { itemCart ->
                        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                            ProductItem(itemCart)
                        }
                    }
                }
            }

            is AsyncState.Failure -> {
                val throwable = data.throwable
                if (throwable is UnauthorizedException) {
                    Box(contentAlignment = Alignment.Center) {
                        Button(

                            onClick = {
                                loginAction
                            }
                        ) {
                            Text("Login")
                        }
                    }
                } else {
                    Text(throwable.message!!)
                }
            }

            else -> {

            }
        }

    }
}

@Composable
fun ProductItem(itemCart: CartItemEntity?) {
    val imagePainter = rememberImagePainter(itemCart?.productDetail?.images!!.first())
    Row(
        modifier = Modifier.padding(bottom = 6.dp).background(
            color = Color.Black.copy(alpha = 0.3f),
            shape = RoundedCornerShape(12.dp)
        ).padding(6.dp).fillMaxWidth().height(100.dp)
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
                text = itemCart.productDetail.title,
            )
            Text(
                text = itemCart.price.toRupiah
            )
        }
    }
}