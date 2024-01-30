package com.bintaaaa.features.authentication

import com.bijan.libraries.core.state.AsyncState
import com.bintaaaa.apis.authentication.models.LoginEntity

data class AuthenticationState(
    val login: AsyncState<LoginEntity> = AsyncState.Default,
    val name: String = "",
    val password: String = "",
    val isShowPassword: Boolean = false,
)
