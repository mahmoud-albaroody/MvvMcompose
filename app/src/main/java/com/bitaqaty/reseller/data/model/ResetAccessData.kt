package com.bitaqaty.reseller.data.model

data class ResetAccessData(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    val mobileNumber: String,
    val token: String,
    val validUsername: Boolean
)