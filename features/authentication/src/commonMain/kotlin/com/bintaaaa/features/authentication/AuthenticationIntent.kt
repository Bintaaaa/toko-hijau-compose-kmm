package com.bintaaaa.features.authentication

import com.bijan.libraries.core.state.Intent

sealed class AuthenticationIntent : Intent {
    data object UserLogin : AuthenticationIntent()

    data class UpdateName(val name: String) : AuthenticationIntent()

    data class UpdatePassword(val password: String) : AuthenticationIntent()
}