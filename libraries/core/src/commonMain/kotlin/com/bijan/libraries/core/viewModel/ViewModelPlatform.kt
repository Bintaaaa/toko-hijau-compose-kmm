package com.bijan.libraries.core.viewModel

import androidx.compose.runtime.Composable
import com.bijan.libraries.core.state.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

expect abstract class ViewModelPlatform() {
    val viewModelScope: CoroutineScope

    fun onClear()
}

abstract class ViewModel<S: Any, I: Intent>(initialState: S) : ViewModelPlatform(){
    private val _uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiState: StateFlow<S> get() = _uiState

    abstract fun sendIntent(intent: Intent)

    protected fun updateUiState(block: S.() -> S){
        _uiState.update(block)
    }
}

@Composable
expect  fun <T: ViewModel<*,*>> rememberViewModel(isRetain: Boolean = true, viewModel: () ->  T): T