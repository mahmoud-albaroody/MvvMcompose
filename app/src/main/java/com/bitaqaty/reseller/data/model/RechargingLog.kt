package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang

data class RechargingLog(
    val id: Int = 0,
    var errors: ArrayList<ErrorMessage> = ArrayList(),
    private var date: String = "",
    private var chargingMethod: String = "",
    private var chargingMethodEn: String = "",
    private var chargingMethodAr: String = "",
    private var paymentMethodNameEn: String = "",
    private var paymentMethodNameAr: String = "",
    var postTransactionBalance: Double = 0.0,
    val amount: Double = 0.0,
    private var customerCurrency: String = "",
    private var customerCurrencyAr: String = "",
    val balanceAfter: Double = 0.0,
    val manualRefillReason: String = ""
) {

    fun getCurrency(): String {
        return if (lang == "en") {
            customerCurrency
        } else {
            customerCurrencyAr
        }
    }

    fun getBalanceAfterValue(): Double {
        return if (balanceAfter == 0.0) {
            postTransactionBalance
        } else {
            balanceAfter
        }
    }

    fun getChargingMethod(): String {
        return if (lang == "en") {
            getEnglishChargingMethod()
        } else {
            getArabicChargingMethod()
        }
    }

    private fun getEnglishChargingMethod(): String {
        return when {
            paymentMethodNameEn.isEmpty() && chargingMethodEn.isEmpty() -> {
                if (chargingMethod == "COMMISSION") {
                    "MADA Sales Commission"
                } else {
                    chargingMethod
                }
            }
            paymentMethodNameEn.isEmpty() && chargingMethod.isEmpty() -> {
                if (chargingMethodEn == "COMMISSION") {
                    "MADA Sales Commission"
                } else {
                    chargingMethodEn
                }
            }
            else -> {
                if (paymentMethodNameEn == "COMMISSION") {
                    "MADA Sales Commission"
                } else {
                    paymentMethodNameEn
                }
            }
        }
    }

    private fun getArabicChargingMethod(): String {
        return when {
            paymentMethodNameAr.isEmpty() -> {
                if (chargingMethodAr == "COMMISSION") {
                    "عمولة مبيعات مدى"
                } else {
                    chargingMethodAr
                }
            }
            else -> {
                if (paymentMethodNameAr == "COMMISSION") {
                    "عمولة مبيعات مدى"
                } else {
                    paymentMethodNameAr
                }
            }
        }
    }

    fun getCheckingDate(): String {
        return date.replace(",", "")
    }
}
