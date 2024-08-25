package com.bitaqaty.reseller.data.model

data class DataResult(
    var loginProcessToken: String? = null,
    var resellerProfitType: String? = null
) : StatusResponse()
