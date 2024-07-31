package com.bitaqaty.reseller.data.model

data class ForgetPasswordSend(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    val resetPasswordToken: String?
)
