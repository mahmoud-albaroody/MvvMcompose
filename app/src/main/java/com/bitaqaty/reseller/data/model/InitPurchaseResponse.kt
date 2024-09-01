package com.bitaqaty.reseller.data.model

data class InitPurchaseResponse(
        val paymentRefNumber: String,
        var data: InvalidItem? = null,
        override var errors: ArrayList<ErrorMessage>? = null,
) : StatusResponse
