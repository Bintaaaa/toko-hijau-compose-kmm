
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.bijan.apis.product.LocalProductRepository
import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.viewModel.LocalViewModelHost
import com.bijan.libraries.core.viewModel.ViewModelHost
import com.example.features.home.Home
import com.example.productdetail.ProductDetailScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
    val appConfigProvider = remember { AppConfigProvider() }
    val productRepository = remember { ProductRepository(appConfigProvider) }


    PreComposeApp{
        CompositionLocalProvider(
            LocalViewModelHost provides  viewModelHost,
            LocalAppConfig provides appConfigProvider,
            LocalProductRepository provides productRepository,
        ){
            MaterialTheme {
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = "/home"
                ) {
                    scene(
                        route = "/home"
                    ) {
                        Home {
                            navigator.navigate("/detail/${it.name}")
                        }
                    }

                    scene(
                        route = "/detail/{name}"
                    ) {
                        val name = it.pathMap["name"].orEmpty()

                        ProductDetailScreen (name)
                    }

                }
            }
        }
    }
}