package com.bitaqaty.reseller.data.model

data class SearchBank(
    val requestTotalCount: Int = 0,
    val requestsLogs: List<RequestsLog>? = null
)