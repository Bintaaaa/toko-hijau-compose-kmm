package com.bijan.libraries.core.viewModel

import androidx.compose.runtime.compositionLocalOf

@OptIn(ExperimentalStdlibApi::class)
class ViewModelHost : AutoCloseable {
    private  val viewModelsPlatform = mutableMapOf<String,ViewModel<*>>()

    fun <T: ViewModel<*>>getViewModel(viewModel: T) : T{
        val key = viewModel::class.simpleName.orEmpty()
        return viewModelsPlatform.getOrPut(key){
            viewModel
        } as T
    }
    override fun close() {
        viewModelsPlatform.onEach {
            it.value.onClear()
        }
        viewModelsPlatform.clear()
    }

}

val LocalViewModelHost = compositionLocalOf<ViewModelHost>{ error("ViewModelHost Not Provided")  }