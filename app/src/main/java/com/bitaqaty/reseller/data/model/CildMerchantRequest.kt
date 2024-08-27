package com.bitaqaty.reseller.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChildMerchantRequest(
    @SerializedName("catrgoryId")
    val categoryId: Int,
    val pageNumber: Int = 1,
    val pageSize: Int = 10000
) : Serializable
