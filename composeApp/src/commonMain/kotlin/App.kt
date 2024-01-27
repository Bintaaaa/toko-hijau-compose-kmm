
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.bijan.apis.product.LocalProductRepository
import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.viewModel.LocalViewModelHost
import com.bijan.libraries.core.viewModel.ViewModelHost
import com.example.libraries.components.utils.LocalImageResouceUtils
import com.example.productdetail.ProductDetailScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }
    val productRepository = remember { ProductRepository(appConfigProvider) }
    val imageResourcesProvider = remember { ImageResourcesProvider() }

    val bottomScreenProvider = remember { BottomScreenNavigator() }

    PreComposeApp{
        CompositionLocalProvider(
            LocalViewModelHost provides  viewModelHost,
            LocalAppConfig provides appConfigProvider,
            LocalImageResouceUtils provides imageResourcesProvider,
            LocalProductRepository provides productRepository,
            LocalBottomScreen provides  bottomScreenProvider,
        ){
            MaterialTheme {
                PreComposeApp {
                    val navigator = rememberNavigator()
                    NavHost(
                        navigator = navigator,
                        navTransition = NavTransition(),
                        initialRoute = "/home"
                    ) {
                        scene(
                            route = "/home"
                        ) {
                           BottomScreen()
                        }

                        scene(
                            route = "/detail/{id}"
                        ) {
                            val id = it.pathMap["id"].orEmpty()

                            ProductDetailScreen (id = id ){
                                navigator.popBackStack()
                            }
                        }

                    }
                }
            }
        }
    }
}