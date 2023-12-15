package com.bijan.libraries.core.viewModel

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel() {
    val viewModelScope: CoroutineScope

    fun onClear()
}
@Composable
expect  fun <T: ViewModel> rememberViewModel(isRetain: Boolean = true, viewModel: () ->  T): T