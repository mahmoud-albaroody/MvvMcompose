package com.bitaqaty.reseller.data.model

data class ProductListResult(
    var errors: List<ErrorMessage> = ArrayList(),
    val products: ArrayList<Product> = ArrayList(),
    val totalElementsCount: Int = 0,
    val disabledMerchant: Boolean = false
)
