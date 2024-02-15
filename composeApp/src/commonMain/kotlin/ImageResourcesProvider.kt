import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.example.libraries.components.utils.ImageResourcesUtils
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
class ImageResourcesProvider : ImageResourcesUtils {

    @Composable
    override fun ArrowBack(): Painter {
        return painterResource("round_arrow_back_24.xml")
    }

    @Composable
    override fun StarFill(): Painter {
        return painterResource("round_star_24.xml")
    }

    @Composable
    override fun StarBorder(): Painter {
        return painterResource("round_star_border_24.xml")
    }

    @Composable
    override fun logos(): Painter {
        return painterResource("toko_hijau_logo.png")
    }

    @Composable
    override fun cart(): Painter {
        return painterResource("round_shopping_cart_24.xml")
    }

    @Composable
    override  fun profile(): Painter{
        return painterResource("profile_blank.png")
    }
}