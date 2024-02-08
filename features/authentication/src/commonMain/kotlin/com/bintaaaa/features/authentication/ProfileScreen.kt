package com.bintaaaa.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.libraries.components.utils.LocalImageResouceUtils

@Composable
fun ProfileScreen(){
    val imageResourcesProvider = LocalImageResouceUtils.current
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Image(
            imageResourcesProvider.profile(),
            contentDescription = null,
        )
    }
}