package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals

data class RequestBankTransferLogBody(
    var pageSize: Int = 10,
    var pageNumber: Int = 1,
    var dateFrom: String? = null,
    var dateTo: String? = null,
    var requestStatus: String = Globals.BankTransferStatus.ALL.value,
    var timeZone: String? = null

    )
