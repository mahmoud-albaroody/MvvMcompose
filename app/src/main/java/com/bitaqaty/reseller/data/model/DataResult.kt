package com.bitaqaty.reseller.data.model

data class DataResult(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    var loginProcessToken: String? = null,
    var resellerProfitType: String? = null
)
