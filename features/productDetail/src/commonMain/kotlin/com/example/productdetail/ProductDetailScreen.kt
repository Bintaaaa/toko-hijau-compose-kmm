package com.example.productdetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun ProductDetailScreen(name: String){
    Text(
        text = name,
    )
}