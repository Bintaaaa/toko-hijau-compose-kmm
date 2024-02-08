import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bintaaaa.features.favorite.FavoriteScreen
import com.example.features.home.Home
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator


enum class ItemBottomScreen {
    HOME, FAVORITE
}

class BottomScreenNavigator {
    var currentScreen by mutableStateOf(ItemBottomScreen.HOME)
}

val LocalBottomScreen = compositionLocalOf<BottomScreenNavigator> { error("Tab navigator not provided") }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.BottomScreenItem(
    item: ItemBottomScreen,
    paggerState: PagerState,
    iconActive: ImageVector,
    iconInActive: ImageVector,
) {
    val tabNavigator = LocalBottomScreen.current
    val isSelected by derivedStateOf { tabNavigator.currentScreen == item }
    val scope = rememberCoroutineScope()
    BottomNavigationItem(
        selected = isSelected,
        onClick = {
            val page = when (item) {
                ItemBottomScreen.HOME -> 0
                ItemBottomScreen.FAVORITE -> 1
            }
            scope.launch {
                paggerState.animateScrollToPage(page)
            }
        },
        icon = {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (isSelected) iconActive else iconInActive,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) Color.Green.copy(alpha = 0.3f) else Color.Gray
                )
                Text(
                    text = item.name.lowercase(), style = TextStyle(
                        color = if (isSelected) Color.Green.copy(alpha = 0.3f) else Color.Gray
                    )
                )
            }
        },
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomScreen(navigator: Navigator) {
    val paggerState = rememberPagerState { 2 }

    val tabNavigator = LocalBottomScreen.current

    when (paggerState.currentPage) {
        0 -> {
            tabNavigator.currentScreen = ItemBottomScreen.HOME
        }

        1 -> {
            tabNavigator.currentScreen = ItemBottomScreen.FAVORITE
        }

    }

    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White,
        ) {
            BottomScreenItem(
                ItemBottomScreen.HOME,
                paggerState,
                iconInActive = Icons.Outlined.Home,
                iconActive = Icons.Rounded.Home,
            )
            BottomScreenItem(
                ItemBottomScreen.FAVORITE, paggerState, iconInActive = Icons.Outlined.Favorite,
                iconActive = Icons.Rounded.Favorite,
            )
        }
    }) {

        HorizontalPager(
            state = paggerState,
            beyondBoundsPageCount = 2,
        ) { index ->
            when (index) {
                0 -> {
                    Home(onItemClick = {
                        navigator.navigate("/detail/${it.id}")
                    }, onCart = {
                        navigator.navigate("/cart")
                    })
                }

                1 -> {
                    FavoriteScreen {
                        navigator.navigate("/detail/${it.id}")
                    }
                }
            }
        }
    }
}