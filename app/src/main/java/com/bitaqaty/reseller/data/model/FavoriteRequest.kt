package com.bitaqaty.reseller.data.model

data class FavoriteRequest(
    val customerId: Int? = CurrentUser.getInstance()?.reseller?.id,
    val productId: Int
)
