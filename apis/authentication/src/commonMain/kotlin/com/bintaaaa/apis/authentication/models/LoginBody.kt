package com.bintaaaa.apis.authentication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody (
    @SerialName("name")
    val name: String?,

    @SerialName("password")
    val password: String?
)