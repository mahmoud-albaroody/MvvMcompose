package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import java.io.Serializable

data class ReportLog(
    var errors: ArrayList<ErrorMessage>? = null,
    var elements: ArrayList<Report>? = null,
    var totalElementsCount: Int? = null,
    var transactionsTotalAmount: Double? = null,
    var transactionsTotalAmountForRecommendedPrice: Double? = null,
    var numberOfTransactions: Int = 0,
    var totalRecommendedPrice: Double? = null,
    var totalExpectedProfit: Double? = null,
    var totalProfit: Double? = null,
    var totalTransAmount: Double? = null,
    var totalSubResellerPrice: Double? = null,
    var madacommission: Double? = null,
    var balanceCommission: Double? = null,
    var price: Double? = null
) : Serializable {

    constructor(errors: ErrorMessage) : this(errors = arrayListOf(errors))

    fun getTotalSubResellerPrice(): String {
        return if (totalSubResellerPrice == null || totalSubResellerPrice == 0.0) {
            "N/A"
        } else {
            "${totalSubResellerPrice.toString().fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalRecommendedPrice(): String {
        return if (totalRecommendedPrice == null || totalRecommendedPrice == 0.0) {
            "N/A"
        } else {
            "${totalRecommendedPrice.toString().fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalExpectedProfit(): String {
        return if (totalExpectedProfit == null) {
            "N/A"
        } else {
            "${totalExpectedProfit.toString().fmt()} ${Utils.getUserCurrency()}"
        }
    }

    fun getTotalProfit(): String {
        return if (totalProfit == null) {
            "N/A"
        } else {
            if (totalProfit.toString().fmt() == "-0") {
                "${0.toString().fmt()} ${Utils.getUserCurrency()}"
            } else {
                "${totalProfit.toString().fmt()} ${Utils.getUserCurrency()}"
            }
        }
    }

    fun price(): String {
        return if (price == null || totalTransAmount == 0.0) {
            "N/A"
        } else {
            "${(price?.fmt())} ${Utils.getUserCurrency()}"
        }
    }
}