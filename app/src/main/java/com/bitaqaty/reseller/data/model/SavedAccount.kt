package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import java.io.Serializable

data class SavedAccount(
    val bankAccountNumber: String = "",
    val fromBankId: String,
    val fromCountryId: String,
    val resellerBankCountryAr: String,
    val resellerBankCountryEn: String,
    val resellerBankName: String,
    val resellerBankNameAr: String,
    var selected: Boolean,
    val senderName: String = ""
) : Serializable {
    fun getBankName(): String {
        return if (Globals.lang == "en") {
            resellerBankName ?: resellerBankNameAr ?: ""
        } else {
            resellerBankNameAr ?: resellerBankName ?: ""
        }
    }

    fun getCountryName(): String {
        return if (Globals.lang == "en") {
            resellerBankCountryEn ?: resellerBankCountryAr ?: ""
        } else {
            resellerBankCountryAr ?: resellerBankCountryEn ?: ""
        }
    }
}