package com.bintaaaa.apis.authentication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: DataResponse?
) {
    @Serializable
    data class DataResponse(
        @SerialName("token")
        val token: String?
    )
}
