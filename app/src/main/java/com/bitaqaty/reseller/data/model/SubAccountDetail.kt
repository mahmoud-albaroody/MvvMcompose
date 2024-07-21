package com.bitaqaty.reseller.data.model

import java.io.Serializable
import java.util.Locale

data class SubAccountDetail(
    var purchaselimit: Double? = 0.0,
    var purchaseTotal: Double? = 0.0,
    var description: String? = null,
    var subResellerType: String? = null,
    var subResellerTypeDuration: String? = null,
    var otpEnabled: Boolean? = null,
    var currentRemainingLimit: Double? = null
) : Serializable {

    override fun toString(): String {
        return "SubAccountDetail(purchaselimit=$purchaselimit, purchaseTotal=$purchaseTotal, description='$description', subResellerType='$subResellerType', subResellerTypeDuration='$subResellerTypeDuration', otpEnabled=$otpEnabled, currentRemainingLimit=$currentRemainingLimit)"
    }

    fun getPurchaseLimit(): String {
        return if (purchaselimit != 0.0) {
            "%.2f".format(Locale("en"), purchaselimit)
        } else {
            "0.0"
        }
    }

    fun getCurrentRemaining(): String {
        return if (currentRemainingLimit != 0.0) {
            "%.2f".format(Locale("en"), currentRemainingLimit)
        } else {
            "0.0"
        }
    }

    fun getPurchaseTotal(): String {
        return if (purchaseTotal != 0.0) {
            "%.2f".format(Locale("en"), purchaseTotal)
        } else {
            "0.0"
        }
    }

    fun getPurchaseLimitDouble(): Double {
        return if (purchaselimit != 0.0) {
            val result = "%.2f".format(Locale("en"), purchaselimit)
            result.toDouble()
        } else {
            0.0
        }
    }

    fun getCurrentRemainingDouble(): Double {
        return if (currentRemainingLimit != 0.0) {
            val result = "%.2f".format(Locale("en"), currentRemainingLimit)
            result.toDouble()
        } else {
            0.0
        }
    }
}
