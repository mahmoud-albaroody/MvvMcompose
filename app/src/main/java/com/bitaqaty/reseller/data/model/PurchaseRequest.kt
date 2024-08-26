package com.bitaqaty.reseller.data.model

import android.icu.util.TimeZone
import android.os.Build
import com.google.gson.annotations.SerializedName

data class PurchaseRequest(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("cart")
    val isCart: Boolean = false,
    @SerializedName("purchaseProductRequestDTOList")
    val productInfo: ArrayList<ProductInfo>
)

data class ProductInfo(
    val timeZone: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) TimeZone.getDefault().id else "",
    val resellerId: Int = 312717,
    val productId: Int,
    val currentPrice: String,
    val itemsCount: Int
)
