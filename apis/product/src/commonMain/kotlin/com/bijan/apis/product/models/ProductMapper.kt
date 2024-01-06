package com.bijan.apis.product.models

import com.bijan.apis.product.models.ProductsResponseModel.ProductItemResponseModel

object ProductMapper {
    fun mapResponseToList(productListResponse: ProductsResponseModel): List<ProductResponseModel> {
        return  productListResponse?.data?.map { mapItemResponseToItemList(it) }.orEmpty()
    }

    private fun mapItemResponseToItemList(itemResponse: ProductItemResponseModel?): ProductResponseModel{
        return ProductResponseModel(
            id = itemResponse?.id  ?: 0,
            name = itemResponse?.name ?: "--",
            price = itemResponse?.price ?: 0.0
        )
    }
}