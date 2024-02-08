package com.example.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.apis.product.repository.LocalProductRepository
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.components.ProductItemComponent
import com.example.libraries.components.components.TopBarComponent


@Composable
fun Home(onItemClick: (ProductResponseEntity) -> Unit, onCart: () -> Unit) {
    val appConfig = LocalAppConfig.current
    val productRepository = LocalProductRepository.current

    val homeViewModel = rememberViewModel {
        HomeViewModel(productRepository, appConfig)
    }

    val homeState by homeViewModel.uiState.collectAsState()

    val pagingProduct = homeState.pagingData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        homeViewModel.sendIntent(
            HomeIntent.GetProductsLowPrice
        )
        homeViewModel.sendIntent(
            HomeIntent.GetCategories
        )
    }

    Scaffold(
        topBar = {
            TopBarComponent("Selamat Datang di Toko Hijau!", onCart = {
                onCart.invoke()
            })
        }
    ) {
        Column(
            Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
            CategoriesSection(homeState)
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Produk relevan", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            ProductsLowPriceSection(homeState, pagingProduct) { product ->
                onItemClick.invoke(product)
            }
        }
    }
}

@Composable
fun ProductsLowPriceSection(
    homeState: HomeState,
    pagingProduct: LazyPagingItems<ProductResponseEntity>,
    onItemClick: (ProductResponseEntity) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp).heightIn(min = 300.dp, max = 1200.dp),
    ) {
        LazyColumn {
            items(pagingProduct.itemCount) { index ->
                val item = pagingProduct[index]
                if (item != null) {
                    ProductItemComponent(item) { product ->
                        onItemClick.invoke(product)
                    }
                }
            }
            when {
                pagingProduct.loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                pagingProduct.loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                pagingProduct.loadState.refresh is LoadState.Error -> {
                    item {
                        val throwable = (pagingProduct.loadState.refresh as LoadState.Error).error
                        Text(throwable.message.orEmpty())
                    }
                }

                pagingProduct.loadState.append is LoadState.Error -> {
                    item {
                        val throwable = (pagingProduct.loadState.append as LoadState.Error).error
                        Text(throwable.message.orEmpty())
                    }
                }
            }
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

