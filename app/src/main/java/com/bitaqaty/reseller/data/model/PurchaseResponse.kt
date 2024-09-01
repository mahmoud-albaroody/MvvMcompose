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
    val successMessage: String? = null,
    val totalRecommendedPrice: String? = null,
    val totalRecommendedPriceAfterVat: String? = null
){
    fun getTotalRecommendedRetailPriceWithCurrency(): String {
        return "${totalRecommendedPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailAfterVatWithCurrency(): String {
        return "${totalRecommendedPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }
}

data class PurchaseResponseProductDetails(
    val purchaseProductResponseDTO: ProductDetails,
    val productType: String,
    val recommendedRetailPrice: String,
    val recommendedRetailPriceAfterVat: String?
){
    fun getRecommendedRetailPriceWithCurrency(): String {
        return "${recommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }
}

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
