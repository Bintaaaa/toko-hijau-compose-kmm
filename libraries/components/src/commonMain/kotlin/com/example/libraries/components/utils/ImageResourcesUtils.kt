package com.example.libraries.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.painter.Painter

interface ImageResourcesUtils {
    @Composable
    fun ArrowBack(): Painter

    @Composable
    fun StarFill(): Painter

    @Composable
    fun StarBorder(): Painter
}

val LocalImageResouceUtils = compositionLocalOf <ImageResourcesUtils>{  error("Image resource not provided")  }