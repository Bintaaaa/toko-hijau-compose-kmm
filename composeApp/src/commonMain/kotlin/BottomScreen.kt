import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.bintaaaa.features.favorite.FavoriteScreen
import com.example.features.home.Home
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.rememberNavigator


enum class ItemBottomScreen {
    HOME, FAVORITE
}

class BottomScreenNavigator {
    var currentScreen by mutableStateOf(ItemBottomScreen.HOME)
}

val LocalBottomScreen = compositionLocalOf<BottomScreenNavigator> { error("Tab navigator not provided") }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.BottomScreenItem(item: ItemBottomScreen, paggerState: PagerState) {
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
            Text(
                text = item.name.lowercase()
            )
        },
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomScreen() {
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
        BottomNavigation {
            BottomScreenItem(ItemBottomScreen.HOME, paggerState)
            BottomScreenItem(ItemBottomScreen.FAVORITE, paggerState)
        }
    }) {
        val navigator = rememberNavigator()

        HorizontalPager(
            state = paggerState,
            beyondBoundsPageCount = 2,
        ) { index ->
            when (index) {
                0 -> {
                    Home {
                        navigator.navigate("/detail/${it.id}")
                    }
                }

                1 -> {
                    FavoriteScreen()
                }
            }
        }
    }
}