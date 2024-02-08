package com.example.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijan.apis.product.models.product.UserReview
import com.bijan.apis.product.repository.LocalProductRepository
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.components.TopBarComponent
import com.example.libraries.components.utils.LocalImageResouceUtils
import com.example.libraries.components.utils.toRupiah
import com.seiko.imageloader.rememberImagePainter


@Composable
fun ProductDetailScreen(id: String, actionBack: () -> Unit) {
    val productRepository  = LocalProductRepository.current


    val productDetailViewModel = rememberViewModel {
        ProductDetailViewModel(productRepository)
    }

    val productDetailState by productDetailViewModel.uiState.collectAsState()

    val imageResources = LocalImageResouceUtils.current

    val imageFavorite = if (productDetailState.isFavorite) {
        imageResources.StarFill()
    } else {
        imageResources.StarBorder()
    }

    LaunchedEffect(Unit) {
        productDetailViewModel.sendIntent(
            ProductDetailIntent.GetProductDetail(id.toInt())
        )
    }

    Scaffold(topBar = { TopBarComponent(title = "Produk Detail", onBack = actionBack) }) {
        LazyColumn {
            item {
                CarouselImagesProductDetailSection(productDetailState)
            }
            item {
                CommonInformationProductDetailSection(
                    productDetailState,
                    imageFavorite = imageFavorite,
                    viewModel = productDetailViewModel
                )
            }
            item {
                ReviewProductDetailSection(productDetailState)
            }
        }
    }
}


@Composable
fun CarouselImagesProductDetailSection(productDetailState: ProductDetailState) {
    when (val dataImages = productDetailState.productDetail) {
        is AsyncState.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncState.Success -> {
            LazyRow {
                val images = dataImages.data.images
                items(images) { image ->
                    val imagePainter = rememberImagePainter(image)
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(200.dp).fillParentMaxWidth()
                    )
                }
            }

        }

        else -> {}
    }
}

@Composable
fun CommonInformationProductDetailSection(
    productDetailState: ProductDetailState,
    imageFavorite: Painter,
    viewModel: ProductDetailViewModel
) {
    when (val dataCommon = productDetailState.productDetail) {
        is AsyncState.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncState.Success -> {
            val commonInfo = dataCommon.data
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${commonInfo.rating}/5",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                    Text(
                        commonInfo.price.toRupiah,
                        fontSize = 24.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(6.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        commonInfo.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(6.dp)
                    )
                    IconButton(
                        onClick = {
                            viewModel.sendIntent(ProductDetailIntent.ToggleFavorite(commonInfo))
                        }
                    ) {
                        Icon(
                            painter = imageFavorite,
                            contentDescription = null
                        )
                    }

                }
                Text(
                    commonInfo.description,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }

        }

        else -> {}
    }


}

@Composable
fun ReviewProductDetailSection(productDetailState: ProductDetailState) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(
            "Ulasan dari pelanggan", fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )
        when (val reviewUser = productDetailState.productDetail) {
            is AsyncState.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncState.Success -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val userReview = reviewUser.data.userReview
                    items(userReview) { review ->
                        UserReviewItems(userReview = review)
                    }
                }

            }

            else -> {}
        }

    }
}

@Composable
fun UserReviewItems(userReview: UserReview) {
    Card(modifier = Modifier.width(400.dp)) {
        Column {
            Text(
                userReview.user,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                userReview.review,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}