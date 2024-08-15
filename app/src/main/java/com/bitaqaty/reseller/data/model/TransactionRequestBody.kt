package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import java.io.Serializable

data class TransactionRequestBody(
    var pageSize: Int = Globals.PAGE_SIZE,
    var pageNumber: Int = 1,
    var subAccountId: Int? = null,
    var searchPeriod: String? = Globals.DATE.CURRENT_MONTH.value,
    var customDateFrom: String? = null,
    var customDateTo: String? = null,
    var channel: String? = null,
    var isPrinted :String?=null,
    var productSecret :String?=null,
    var productSerial :String?=null,
    var paymentMethod: String? = null,
    var showTotal: Boolean? = null,
    var pinCode:String?=null,
    var serialNo:String?=null
) : Serializable