package com.bitaqaty.reseller.data.model

data class ResetAccessData(
    val mobileNumber: String,
    val token: String,
    val validUsername: Boolean,
    override var errors: ArrayList<ErrorMessage>? = null,
) : StatusResponse