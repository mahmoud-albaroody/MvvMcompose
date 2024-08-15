package com.bitaqaty.reseller.data.model

data class PaymentStatus(
    val chargeValid: Boolean,
    val paymentStatus: String,
    val payment_ref_number: String,
    val transactionNumber: String,
    val body: String,
    val statusCodeValue: Int,
    val statusCode: String,
)