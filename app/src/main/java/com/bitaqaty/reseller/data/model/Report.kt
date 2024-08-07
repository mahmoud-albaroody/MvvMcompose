package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import java.io.Serializable

data class Report(
    var subAccountName: String? = null,
    var merchantNameAr: String? = null,
    var merchantNameEn: String? = null,
    var productNameAr: String? = null,
    var productNameEn: String? = null,
    var price: Double? = null,
    var numberOfTrans: Int? = null,
    var numberOfTransForRecommendedPrice: Double? = null,
    var totalTransAmount: Double? = null,
    var productSalesPercentage: String? = null,
    var logoPath: String? = null,
    var productId: Int? = null,
    var vatOnRecommendedPrice: Double? = null,
    var totalCostPerItem: Double? = null,
    var totalCost: String? = null,
    var recommendedPrice: Double? = null,
    var totalRecommendedPrice: Double? = null,
    var totalExpectedProfit: Double? = null,
    var totalProfit: Double? = null,
    private val totalSubResellerPrice: Double? = null,
    val subResellerPrice: Double? = null,
    val totalTransAmountForSubResellerPrice: Double? = null,
    var totalTransAmountForRecommendedPrice: Double? = null,
    var subTransactionBalanceAfter: Double? = null,
    var subTransactionAmount: Double? = null,
    var subTransactionActualProfit: Double? = null,
    var paymentMethodAr: String? = null,
    var paymentMethodEn: String? = null
) : Serializable {

    fun getMerchantName(): String {
        return if (lang == "en") {
            merchantNameEn ?: merchantNameAr ?: ""
        } else {
            merchantNameAr ?: merchantNameEn ?: ""
        }
    }

    fun getPaymentMethod(): String {
        return if (lang == "en") {
            paymentMethodEn ?: paymentMethodAr ?: ""
        } else {
            paymentMethodAr ?: paymentMethodEn ?: ""
        }
    }

    fun getTotalSubResellerPrice(): String {
        return if (totalSubResellerPrice == null || totalSubResellerPrice == 0.0) {
            "N/A"
        } else {
            "${totalSubResellerPrice.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun subSubResellerPrice(): String {
        return if (subResellerPrice == null || subResellerPrice == 0.0) {
            "N/A"
        } else {
            "${subResellerPrice.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun subTransactionBalanceAfter(): String {
        return if (subTransactionBalanceAfter == null || subTransactionBalanceAfter == 0.0) {
            "N/A"
        } else {
            "${subTransactionBalanceAfter?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun subTransactionActualProfit(): String {
        return if (subTransactionActualProfit == null || subTransactionActualProfit == 0.0) {
            "N/A"
        } else {
            "${subTransactionActualProfit?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun subTransactionAmount(): String {
        return if (subTransactionAmount == null || subTransactionAmount == 0.0) {
            "N/A"
        } else {
            "${subTransactionAmount?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getProductName(): String {
        return if (lang == "en") {
            productNameEn ?: productNameAr ?: ""
        } else {
            productNameAr ?: productNameEn ?: ""
        }
    }

    fun totalTransAmount(): String {
        return if (totalTransAmount == null) {
            "N/A"
        } else {
            "${totalTransAmount?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun price(): String {
        return if (price == null || totalTransAmount == 0.0) {
            "N/A"
        } else {
            "${price?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalPrice(): String {
        return "${(price ?: 0.0 * (numberOfTrans ?: 0).toDouble()).fmt()} ${Utils.getUserCurrency()}"
    }

    fun getVatOnRecommendedRetailPrice(): String {
        return if (vatOnRecommendedPrice == null) {
            "N/A"
        } else {
            "${vatOnRecommendedPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalCostPerItem(): String {
        return if (totalCostPerItem == null) {
            "N/A"
        } else {
            "${totalCostPerItem?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getRecommendedPrice(): String {
        return if (recommendedPrice == null || recommendedPrice == 0.0) {
            "N/A"
        } else {
            "${recommendedPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalRecommendedPrice(): String {
        return if (totalRecommendedPrice == null || recommendedPrice == 0.0) {
            "N/A"
        } else {
            "${totalRecommendedPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalTransAmountForRecommendedPrice(): String {
        return if (totalTransAmountForRecommendedPrice == null) {
            "N/A"
        } else {
            "${totalTransAmountForRecommendedPrice?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalExpectedProfit(): String {
        return if (totalExpectedProfit == null) {
            "N/A"
        } else {
            "${totalExpectedProfit?.fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalProfit(): String {
        return if (totalProfit == null) {
            "N/A"
        } else {
            "${totalProfit?.fmt()} ${Utils.getUserCurrency()}"
        }
    }
}
