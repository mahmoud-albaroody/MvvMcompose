package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Utils
import com.google.gson.annotations.SerializedName

data class PurchaseResponse(
    @SerializedName("purchaseResponseAndRecommendedPriceList")
    val purchaseProductDetails: ArrayList<PurchaseResponseProductDetails>? = null,
    val resellerCommission:String? = null,
    val errors: ArrayList<ErrorMessage>? = ArrayList(),
    @SerializedName("data")
    val invalidItem: InvalidItem? = null,
    val successMessage: String? = null
)

data class PurchaseResponseProductDetails(
    val purchaseProductResponseDTO: ProductDetails?=null,
    val productType: String?=null,
    val recommendedRetailPrice: String?=null,
    val recommendedRetailPriceAfterVat: String?=null
)

data class InvalidItem(
    val nameAr: String? = null,
    val nameEn: String? = null
) {
    fun getProductName(): String? {
        return if (Utils.getLang() == "ar") {
            nameAr
        } else {
            nameEn
        }
    }
}
