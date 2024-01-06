package com.bijan.libraries.core.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import androidx.lifecycle.ViewModel as LifecycleViewModel
import androidx.lifecycle.viewModelScope as lifecycleViewModelScope

actual abstract  class ViewModelPlatform : LifecycleViewModel() {
    actual val viewModelScope: CoroutineScope
        get() = lifecycleViewModelScope

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
    actual fun onClear() {
        super.onCleared()
        viewModelScope.cancel()
    }

}

@Composable
actual  fun <T: ViewModel<*,*>> rememberViewModel(isRetain: Boolean, viewModel: () ->  T): T{
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val host = LocalViewModelHost.current
    val vm = remember {
        if (isRetain) {
            host.getViewModel(viewModel.invoke())
        } else {
            viewModel.invoke()
        }
    }

    if (!isRetain) {
        DisposableEffect(lifecycle) {
            onDispose {
                vm.onClear()
            }
        }
    }

    return vm
}