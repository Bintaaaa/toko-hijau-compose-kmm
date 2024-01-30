
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.bijan.apis.product.LocalProductRepository
import com.bijan.apis.product.ProductRepository
import com.bijan.libraries.core.LocalAppConfig
import com.bijan.libraries.core.local.AuthenticationLocalDatasource
import com.bijan.libraries.core.local.LocalAuthenticationLocalDatasource
import com.bijan.libraries.core.local.LocalValueDataSources
import com.bijan.libraries.core.local.ValueDataSources
import com.bijan.libraries.core.viewModel.LocalViewModelHost
import com.bijan.libraries.core.viewModel.ViewModelHost
import com.bintaaaa.apis.authentication.AuthenticationRepository
import com.bintaaaa.apis.authentication.LocalAuthenticationRepository
import com.bintaaaa.features.authentication.LoginScreen
import com.example.features.home.SplashScreen
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
    val valueDataSource = remember { ValueDataSources() }
    val authenticationLocalDatasource = remember { AuthenticationLocalDatasource(valueDataSource) }
    val authenticationRepository = remember { AuthenticationRepository(appConfigProvider,authenticationLocalDatasource) }

    PreComposeApp{
        CompositionLocalProvider(
            LocalViewModelHost provides  viewModelHost,
            LocalAppConfig provides appConfigProvider,
            LocalImageResouceUtils provides imageResourcesProvider,
            LocalProductRepository provides productRepository,
            LocalBottomScreen provides  bottomScreenProvider,
            LocalAuthenticationLocalDatasource provides authenticationLocalDatasource,
            LocalAuthenticationRepository provides authenticationRepository,
            LocalValueDataSources provides valueDataSource,
        ){
            MaterialTheme {
                PreComposeApp {
                    val navigator = rememberNavigator()
                    NavHost(
                        navigator = navigator,
                        navTransition = NavTransition(),
                        initialRoute = "/splash"
                    ) {

                        scene(
                            route = "/splash"
                        ) {
                            SplashScreen {
                                navigator.navigate("/login")
                            }
                        }

                        scene(
                            route = "/login"
                        ) {
                            LoginScreen{
                                navigator.navigate("/home")
                            }
                        }

                        scene(
                            route = "/home"
                        ) {
                           BottomScreen(navigator)
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