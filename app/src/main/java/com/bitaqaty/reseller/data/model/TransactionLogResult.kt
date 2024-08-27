package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class TransactionLogResult(
    var transactionLogList: ArrayList<TransactionLog> = ArrayList(),
    var totalElementsCount: Int = 0,
    var totalRetailPrice: Double = 0.0,
    var totalProfit: Double = 0.0,
    var totalCostPrice: Double = 0.0,
    override var errors: ArrayList<ErrorMessage>?=null,
) : StatusResponse
