package com.bitaqaty.reseller.ui.presentation.settlementRequest

import com.bitaqaty.reseller.data.model.ErrorMessage

data class SettlementRequestUiState(
    val amount: String = "",
    val crNum: String = "",
    val companyName: String = "",
    val swiftCode: String = "",
    val bankName: String = "",
    val IBAN: String = "",
    val branchAddress: String = "",
    val notes: String = "",
    val accountNumber: String = "",
    val validation: Validation = Validation(isError = false),
    val errors: Errors = Errors()
)

data class Errors(
    var amountError: Boolean = false,
    var crNumError: Boolean = false,
    var companyNameError: Boolean = false,
    var swiftCodeError: Boolean = false,
    var bankNameError: Boolean = false,
    var IBANError: Boolean = false,
)
