package com.bitaqaty.reseller.data.model

import java.util.ArrayList

data class ValidateResetAccessData(
    val authenticationType: String,
    val validVerificationCode: Boolean,
    override var errors: ArrayList<ErrorMessage>?=null
):StatusResponse
