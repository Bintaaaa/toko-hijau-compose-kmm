package com.bijan.libraries.core.viewModel

import androidx.compose.runtime.compositionLocalOf

@OptIn(ExperimentalStdlibApi::class)
class ViewModelHost : AutoCloseable {
    private  val viewModels = mutableMapOf<String, ViewModel>()

    fun <T: ViewModel>getViewModel(viewModel: T) : T{
        val key = viewModel::class.simpleName.orEmpty()
        return viewModels.getOrPut(key){
            viewModel
        } as T
    }
    override fun close() {
        viewModels.onEach {
            it.value.onClear()
        }
        viewModels.clear()
    }

}

val LocalViewModelHost = compositionLocalOf<ViewModelHost>{ error("ViewModelHost Not Provided")  }