package com.bitaqaty.reseller.data.model

import android.os.Build
import java.io.Serializable
import java.util.TimeZone

data class SettlementRequestDataRequest(
    val timeZone: String = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) TimeZone.getDefault().id else "",
    val amount: String,
    val crNumber: String,
    val companyName: String,
    val swiftCode: String,
    val bankName: String,
    val iban: String,
    val accountNumber: String,
    val branchAddress: String,
    val notes: String
) : Serializable
