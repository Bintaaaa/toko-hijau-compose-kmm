package com.bijan.apis.product.models.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseModel(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val `data`: List<ProductItemResponseModel?>?
) {
    @Serializable
    data class ProductItemResponseModel(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("sort_description")
        val sortDescription: String,
        @SerialName("category")
        val category: Category,
        @SerialName("price")
        val price: Double,
        @SerialName("rating")
        val rating: Double,
        @SerialName("discount")
        val discount: Int,
        @SerialName("images")
        val images: String
    ) {
        @Serializable
        data class Category(
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String,
            @SerialName("description")
            val description: String
        )
    }
}