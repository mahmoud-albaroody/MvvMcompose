package com.bitaqaty.reseller.data.model

data class ProductListResponse(
    val errors: List<ErrorMessage> = ArrayList(),
    val products: ArrayList<Product> = ArrayList(),
    val totalElementsCount: Int = 0,
)