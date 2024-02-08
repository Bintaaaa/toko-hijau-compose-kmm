package com.bintaaaa.features.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijan.libraries.core.local.LocalAuthenticationLocalDatasource
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.bintaaaa.apis.authentication.LocalAuthenticationRepository
import com.example.libraries.components.components.GoToLoginComponent
import com.example.libraries.components.utils.LocalImageResouceUtils

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    val imageResourcesProvider = LocalImageResouceUtils.current
    val authenticationRepository = LocalAuthenticationRepository.current
    val authenticationLocalDatasource = LocalAuthenticationLocalDatasource.current
    val viewModel =
        rememberViewModel { AuthenticationViewModel(authenticationRepository, authenticationLocalDatasource) }
    val authState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(AuthenticationIntent.IsLogin)
    }
    if (authState.isLogin) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Image(
                    imageResourcesProvider.profile(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().size(80.dp).clip(RoundedCornerShape(999.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Selamat Datang Bijan!", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            }

            Button(onClick = {
                onLogout.invoke()
                viewModel.sendIntent(AuthenticationIntent.Logout)
            }, modifier = Modifier.fillMaxWidth().padding(bottom = 70.dp)) {
                Text("Logout")
            }
        }

    } else {

        GoToLoginComponent{
            onLogout.invoke()
        }

    }


}