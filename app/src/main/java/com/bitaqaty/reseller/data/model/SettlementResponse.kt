package com.bitaqaty.reseller.data.model

data class SettlementResponse(
    val requestTotalCount: Int,
    val requestsLogs: ArrayList<SettlementLog>? = arrayListOf()
)