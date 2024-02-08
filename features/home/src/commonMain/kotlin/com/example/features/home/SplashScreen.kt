package com.example.features.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bijan.apis.product.repository.LocalProductRepository
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.state.AsyncState
import com.bijan.libraries.core.viewModel.rememberViewModel
import com.example.libraries.components.utils.LocalImageResouceUtils

@Composable
fun SplashScreen(redirect: () -> Unit) {
    val appConfig = LocalAppConfig.current
    val productRepository = LocalProductRepository.current

    val homeViewModel = rememberViewModel {
        HomeViewModel(productRepository, appConfig)
    }

    val homeState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        homeViewModel.sendIntent(HomeIntent.Splash)
    }

    Scaffold {
        TokoHijauLogo(homeState, redirect)
    }
}

@Composable
fun TokoHijauLogo(homeState: HomeState, redirect: () -> Unit) {
    val imageResourcesProvider = LocalImageResouceUtils.current
   when(homeState.splash){
       is AsyncState.Success ->{
            redirect()
       }
       else -> {
           Box(modifier = Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.Center) {
               Image(painter = imageResourcesProvider.logos(), contentDescription = null)
           }
       }
   }
}