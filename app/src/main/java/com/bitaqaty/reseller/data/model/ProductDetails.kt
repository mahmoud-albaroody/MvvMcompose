package com.bitaqaty.reseller.data.model


import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import java.io.Serializable

data class ProductDetails(
    var errors: ArrayList<ErrorMessage>? = null,
    var products: ArrayList<TransactionLog>? = null,
    var vatAmount: Double = 0.0,
    var totalVatAmount: String? = null,
    var oneItemPriceBeforeVat: Double = 0.0,
    var totalItemsPriceBeforeVat: String? = null,
    var oneItemPriceAfterVat: Double = 0.0,
    var totalItemsPriceAfterVat: String? = null,
    var recommendedRetailPrice: String? = null,
    var totalRecommendedRetailPrice: String? = null,
    var totalRecommendedRetailPriceAfterVat: String? = null,
    var itemsCount: Int = 0,
    var purchaseDate: String = "",
    var purchaseDateTime: String? = null,
    var vatPercentage: String? = null,
    var productId: Int = 0,
    var subResellerPrice: String? = null,
    var subResellerPriceAfterVat: String? = null,
    var subResellerPriceVatAmount: String? = null,
    var totalSubResellerPrice: String? = null,
    var totalSubResellerPriceAfterVat: String? = null,
    var totalSubResellerPriceVatAmount: String? = null,
    var showSKUBarcode: Boolean? = false,
    var skuBarcode: String? = null,
    var resellerCommission: String? = null,
    var surepayCommission: String? = null,
    var oneItemPriceAfterCommission: String? = null,
    var totalItemsPriceAfterCommission: String? = null,
    var vatCode: String? = null,
    var merchantId: String? = null,
    var merchantLogo: String = "",
    val paymentMethodEn: String? = null,
    val productNameEn: String = "",
    val productNameAr: String = "",
) : Serializable {


    fun getProductName(): String {
        return if (Utils.getLang() == "ar") {
            productNameAr
        } else {
            productNameEn
        }
    }

    fun getPriceWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${subResellerPrice ?: ""} ${Utils.getUserCurrency()}"
        else
            "${oneItemPriceBeforeVat ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalPriceWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPrice ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalItemsPriceBeforeVat ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getVatWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPriceVatAmount ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalVatAmount ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalAfterVatWithCurrency(isBalanceReseller: Boolean): String {
        return if (isBalanceReseller)
            "${totalSubResellerPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
        else
            "${totalItemsPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }


    fun getRecommendedRetailPriceWithCurrency(): String {
        return "${recommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailPriceWithCurrency(): String {
        return "${totalRecommendedRetailPrice ?: ""} ${Utils.getUserCurrency()}"
    }

    fun getTotalRecommendedRetailAfterVatWithCurrency(): String {
        return "${totalRecommendedRetailPriceAfterVat ?: ""} ${Utils.getUserCurrency()}"
    }
}

