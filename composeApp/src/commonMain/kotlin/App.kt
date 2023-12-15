import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.bijan.libraries.core.viewModel.LocalViewModelHost
import com.bijan.libraries.core.viewModel.ViewModelHost
import com.example.features.home.Home
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val viewModelHost = remember { ViewModelHost() }
   CompositionLocalProvider(
       LocalViewModelHost provides  viewModelHost
   ){
       MaterialTheme {
           Home()
       }
   }
}