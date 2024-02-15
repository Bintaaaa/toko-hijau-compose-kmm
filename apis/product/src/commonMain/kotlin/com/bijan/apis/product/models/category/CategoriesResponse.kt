package com.bijan.apis.product.models.category


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val `data`: List<Data>
) {
    @Serializable
    data class Data(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("description")
        val description: String
    )
}