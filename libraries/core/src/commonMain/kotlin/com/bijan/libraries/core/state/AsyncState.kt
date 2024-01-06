package com.bijan.libraries.core.state

sealed class AsyncState<out T> {
    data object  Default : AsyncState<Nothing>()
    data object  Loading : AsyncState<Nothing>()

    data class Success<T>(val data: T) : AsyncState<T>()

    data class Failure(val throwable: Throwable) : AsyncState<Nothing>()

}