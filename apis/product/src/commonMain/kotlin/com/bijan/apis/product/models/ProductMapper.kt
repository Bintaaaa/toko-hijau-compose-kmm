package com.bijan.apis.product.models

import com.bijan.apis.product.models.cart.CartItemEntity
import com.bijan.apis.product.models.cart.CartResponseEntity
import com.bijan.apis.product.models.cart.CartResponseModel
import com.bijan.apis.product.models.category.CategoriesResponse
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.local.ProductRealm
import com.bijan.apis.product.models.product.ProductDetailEntity
import com.bijan.apis.product.models.product.ProductDetailResponseModel
import com.bijan.apis.product.models.product.ProductResponseEntity
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.apis.product.models.product.ProductsResponseModel.ProductItemResponseModel
import com.bijan.apis.product.models.product.UserReview

object ProductMapper {
    fun mapResponseToList(productListResponse: ProductsResponseModel): List<ProductResponseEntity> {
        return productListResponse?.data?.map { mapItemResponseToItemList(it) }.orEmpty()
    }

fun mapItemResponseToItemList(itemResponse: ProductItemResponseModel?): ProductResponseEntity {
        return ProductResponseEntity(
            id = itemResponse?.id ?: 0,
            name = itemResponse?.name ?: "--",
            price = itemResponse?.price ?: 0.0,
            image = itemResponse?.images ?: "",
        )
    }

    fun mapResponseCategories(categoriesResponse: CategoriesResponse): List<CategoryItemResponse> {
        return categoriesResponse.data.map { category -> mapItemCategoryItemToCategory(category) }.orEmpty()
    }

    private fun mapItemCategoryItemToCategory(itemResponse: CategoriesResponse.Data): CategoryItemResponse {
        return CategoryItemResponse(
            id = itemResponse.id,
            name = itemResponse.name,
            description = itemResponse.description
        )
    }

    fun mapResponseProductDetail(productDetail: ProductDetailResponseModel.Data): ProductDetailEntity {
        return ProductDetailEntity(
            id = productDetail.id ?: 0,
            title = productDetail.name ?: "-",
            description = productDetail.description ?: "Have no Description",
            price = productDetail.price ?: 0.0,
            rating = productDetail.rating ?: 0.0,
            images = productDetail.images.orEmpty(),
            userReview = productDetail.userReview.map { UserReview(user = it.user ?: "--", review = it.review ?: "No Have Review") }
        )
    }



    fun realMapFromDetail(productDetailEntity: ProductDetailEntity): ProductRealm{
        return ProductRealm().apply {
            id = productDetailEntity.id
            name = productDetailEntity.title
            price = productDetailEntity.price
            description = productDetailEntity.description
            image = productDetailEntity.images[0]
        }
    }


    fun realmMapToItem(realm: ProductRealm): ProductResponseEntity{
        return ProductResponseEntity(
            id = realm.id,
            name = realm.name,
            price = realm.price,
            image = realm.image,
        )
    }

    fun mapResponseToCart(response: List<CartResponseModel.DataResponse>?): List<CartResponseEntity> {
        return response?.map {
            CartResponseEntity(
                productId = it.productId ?: 0,
                price = it.price ?: 0.0,
                discount = it.discount ?: 0,
                amount = it.amount ?: 0.0,
                quantity = it.quantity ?: 0,
            )
        }.orEmpty()
    }

    fun mapCartToCartProduct(cart: CartResponseEntity, productDetail: ProductDetailEntity): CartItemEntity {
        return CartItemEntity(
            amount = cart.amount,
            discount = cart.discount,
            price = cart.price,
            productId = cart.productId,
            quantity = cart.quantity,
            productDetail = productDetail
        )
    }
}