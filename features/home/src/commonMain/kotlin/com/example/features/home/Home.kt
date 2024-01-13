package com.example.features.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijan.apis.product.ProductRepository
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.utils.toRupiah
import com.seiko.imageloader.rememberImagePainter


@Composable
fun Home(onItemClick: (ProductResponseModel) -> Unit) {
    val appConfig = LocalAppConfig.current

    val productRepository = remember {
        ProductRepository(appConfig)
    }
    val homeViewModel = rememberViewModel {
        HomeViewModel(productRepository)
    }

    val homeState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.sendIntent(
            HomeIntent.GetProductsLowPrice
        )
        homeViewModel.sendIntent(
            HomeIntent.GetCategories
        )
    }
//    LazyVerticalStaggeredGrid()
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        CategoriesSection(homeState)
        ProductsLowPriceSection(homeState){ product ->
            onItemClick.invoke(product)
        }
    }
}

@Composable
fun ProductsLowPriceSection(homeState: HomeState, onItemClick: (ProductResponseModel) -> Unit) {
    when (val productList = homeState.asyncProductsLowPrice) {
        is AsyncState.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncState.Failure -> {
            Text(text = productList.throwable.message.orEmpty())
        }

        is AsyncState.Success -> {
            val products = productList.data
            Column(
                modifier = Modifier.height((products.size * 100.5).dp),
            ) {
                Box(modifier = Modifier.padding(12.dp)){
                    Text(
                        "Produk Dengan Harga Rendah", style = TextStyle(
                            fontSize = 16.sp, fontWeight = FontWeight.Bold
                        )
                    )
                }
                LazyColumn(
                    Modifier.padding(12.dp),
                    userScrollEnabled = false
                ) {
                    items(products) { product ->
                        ProductItem(product) { product ->
                            onItemClick.invoke(product)
                        }
                    }
                }
            }
        }

        else -> {}
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
        }.padding(6.dp).fillMaxWidth().height(80.dp)
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

@Composable
fun CategoriesSection(homeState: HomeState) {
    val stateGrid = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.height(100.dp).padding(6.dp),
        columns = GridCells.Fixed(4),
        state = stateGrid,
        userScrollEnabled = false,
    ) {
        when (val asyncCategories = homeState.asyncCategories) {
            is AsyncState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.height(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is AsyncState.Success -> {
                val categories = asyncCategories.data
                items(categories) { category ->
                    CategoryItem(category)
                }
                val moreCategoryItem = CategoryItemResponse(
                    id = -1,
                    name = "More",
                    description = ""
                )
                item {
                    CategoryItem(moreCategoryItem)
                }
            }

            else -> {}
        }
    }
}

@Composable
fun CategoryItem(categoryItemResponse: CategoryItemResponse) {
    Column {
        Box(
            modifier = Modifier.padding(6.dp).size(80.dp).fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(6.dp)).padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryItemResponse.name,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}