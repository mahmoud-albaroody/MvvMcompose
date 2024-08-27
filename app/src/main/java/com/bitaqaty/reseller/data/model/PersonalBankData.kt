package com.bitaqaty.reseller.data.model

data class PersonalBankData(
    val accountNumber: String? = null,
    val amount: Double? = null,
    val bankName: String? = null,
    val branchAddress: String? = null,
    val companyName: String? = null,
    val crNumber: String? = null,
    val creationDate: String? = null,
    val iban: String? = null,
    val id: Int? = null,
    val notes: String? = null,
    val profitType: String? = null,
    val receiptLink: Any? = null,
    val rejectReason: Any? = null,
    val reseller: Int? = null,
    val status: String? = null,
    val swiftCode: String? = null
)
