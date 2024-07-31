package com.bitaqaty.reseller.data.model

data class ValidateResetAccessData(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    val authenticationType: String,
    val validVerificationCode: Boolean
)
