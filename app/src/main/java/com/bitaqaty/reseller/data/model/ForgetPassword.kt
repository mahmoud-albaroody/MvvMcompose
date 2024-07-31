package com.bitaqaty.reseller.data.model

data class ForgetPassword(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    val accountType: String,
    val mobileNumber: String
)
