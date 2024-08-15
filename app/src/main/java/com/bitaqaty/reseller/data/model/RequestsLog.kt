package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang

data class RequestsLog(
    val amount: String,
    val bankNameAr: String,
    val bankNameEn: String,
    val creationDate: String,
    val currencyAr: String,
    val currencyEn: String,
    val rejectionReason: Any,
    val status: String,
    val transferDate: String,
    val transferPersonName: String
) {
    fun getCurrentBankName(): String {
        return if (lang == "en") {
            bankNameEn
        } else {
            bankNameAr
        }
    }
}