package com.bijan.apis.product.models

import com.bijan.apis.product.models.category.CategoriesResponse
import com.bijan.apis.product.models.category.CategoryItemResponse
import com.bijan.apis.product.models.product.ProductResponseModel
import com.bijan.apis.product.models.product.ProductsResponseModel
import com.bijan.apis.product.models.product.ProductsResponseModel.ProductItemResponseModel

object ProductMapper {
    fun mapResponseToList(productListResponse: ProductsResponseModel): List<ProductResponseModel> {
        return  productListResponse?.data?.map { mapItemResponseToItemList(it) }.orEmpty()
    }

    private fun mapItemResponseToItemList(itemResponse: ProductItemResponseModel?): ProductResponseModel {
        return ProductResponseModel(
            id = itemResponse?.id  ?: 0,
            name = itemResponse?.name ?: "--",
            price = itemResponse?.price ?: 0.0,
            image = itemResponse?.images ?: "",
        )
    }

    fun mapResponseCategories(categoriesResponse: CategoriesResponse): List<CategoryItemResponse>{
        return  categoriesResponse.data.map { category ->  mapItemCategoryItemToCategory(category)}.orEmpty()
    }

    private fun mapItemCategoryItemToCategory(itemResponse: CategoriesResponse.Data): CategoryItemResponse{
        return  CategoryItemResponse(
            id = itemResponse.id,
            name = itemResponse.name,
            description = itemResponse.description
        )
    }
}