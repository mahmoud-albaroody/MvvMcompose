package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class ProductListRequest(
    val resellerId: Int = 312717,
    val categoryId: Int? = null,
    val merchantId: Int? = null,
    val orderByProducts: Boolean = true,
    val calculateVat: Boolean = true,
    val applyPagination: Boolean = false,
    val searchCriteria: String? = null
) : Serializable