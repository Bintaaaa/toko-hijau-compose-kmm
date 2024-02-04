package com.example.libraries.components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.libraries.components.utils.LocalImageResouceUtils

@Composable
fun TopBarComponent(
    title: String,
    enableElevation: Boolean = true,
    onBack: (() -> Unit)? = null,
    onCart: (() -> Unit)? = null,
) {
    val imageResourceProvider = LocalImageResouceUtils.current

    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            if (onBack != null) {
                IconButton(
                    onClick = {
                        onBack.invoke()
                    }
                ) {
                    Icon(
                        painter = imageResourceProvider.ArrowBack(),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
            Text(
                text = title
            )
        }
        if (onCart != null) {
            Text(
                text = "Cart",
                Modifier.clickable{
                    onCart.invoke()
                }
            )

        }
    }
}