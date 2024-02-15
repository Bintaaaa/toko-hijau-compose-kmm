package com.example.libraries.components.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.example.libraries.components.utils.toRupiah
import com.seiko.imageloader.rememberImagePainter

@Composable
fun ProductItemComponent(product: ProductResponseEntity, onItemClick: (ProductResponseEntity) -> Unit) {
    val imagePainter = rememberImagePainter(product.image)
    Row(
        modifier = Modifier.padding(bottom = 6.dp)
            .border(width = 0.3.dp, color = Color.Gray.copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
            .clickable {
                onItemClick.invoke(product)
            }.padding(6.dp).fillMaxWidth().clip(RoundedCornerShape(16.dp)).height(100.dp)
    ) {
        Box(Modifier.width(120.dp).padding(end = 12.dp)) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Column {
            Text(
                text = product.name,
                style = TextStyle(
                    fontSize = 16.sp,
                )
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = product.price.toRupiah,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            )
        }
    }
}