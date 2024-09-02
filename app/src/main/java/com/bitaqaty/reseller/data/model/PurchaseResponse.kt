package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Utils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PurchaseResponse(

    val purchaseResponseAndRecommendedPriceList: ArrayList<PurchaseResponseProductDetails>? = null,
    val resellerCommission:String? = null,
    val errors: ArrayList<ErrorMessage>? = null,
    val data: InvalidItem? = null,
    val successMessage: String? = null,
    val totalRecommendedPrice: String? = null,
    val totalRecommendedPriceAfterVat: String? = null
):Serializable{
    fun getTotalRecommendedRetailPriceWithCurrency(): String {
        return "${totalRecommendedPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailAfterVatWithCurrency(): String {
        return "${totalRecommendedPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }
}

data class PurchaseResponseProductDetails(
    val purchaseProductResponseDTO: ProductDetails?=null,
    val productType: String?=null,
    val recommendedRetailPrice: String?=null,
    val recommendedRetailPriceAfterVat: String?=null
):Serializable{
    fun getRecommendedRetailPriceWithCurrency(): String {
        return "${recommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }
}


data class InvalidItem(
    val nameAr: String? = null,
    val nameEn: String? = null
) :Serializable{
    fun getProductName(): String? {
        return if (Utils.getLang() == "ar") {
            nameAr
        } else {
            nameEn
        }
    }
}
