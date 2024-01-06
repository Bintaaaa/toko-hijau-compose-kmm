package com.example.features.home

import com.bijan.libraries.core.state.Intent

sealed class HomeIntent : Intent {
    data class SetName(val name: String): HomeIntent()
    data object GetProducts : HomeIntent()
}