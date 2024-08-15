package com.bitaqaty.reseller.data.model

data class SavedAccount(
    val bankAccountNumber: String,
    val fromBankId: String,
    val fromCountryId: String,
    val resellerBankCountryAr: String,
    val resellerBankCountryEn: String,
    val resellerBankName: String,
    val resellerBankNameAr: String,
    val selected: Boolean,
    val senderName: String
)