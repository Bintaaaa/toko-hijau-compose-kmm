package com.bijan.apis.product.models.product


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponseModel(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val `data`: Data?
) {
    @Serializable
    data class Data(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("category")
        val category: Category?,
        @SerialName("price")
        val price: Double?,
        @SerialName("rating")
        val rating: Double?,
        @SerialName("discount")
        val discount: Int?,
        @SerialName("images")
        val images: List<String>?,
        @SerialName("user_review")
        val userReview: List<UserReview>
    ) {
        @Serializable
        data class Category(
            @SerialName("id")
            val id: Int?,
            @SerialName("name")
            val name: String?,
            @SerialName("description")
            val description: String?
        )

        @Serializable
        data class UserReview(
            @SerialName("user")
            val user: String?,
            @SerialName("review")
            val review: String?
        )
    }
}