package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Globals.PAGE_SIZE
import java.io.Serializable

data class ReportRequestBody(
    var pageSize: Int = PAGE_SIZE,
    var pageNumber: Int = 1,
    var subAccountId: Int? = null,
    var categoryId: Int? = null,
    var merchantId: Int? = null,
    var productId: Int? = null,
    var searchPeriod: String? = Globals.DATE.CURRENT_MONTH.value,
    var customDateFrom: String? = null,
    var customDateTo: String? = null,
    var channel: String? = null,
    var forTopSelling: Boolean? = null,
    var showRecommendedPrices: Boolean? = null,
    var showSubResellerPrices: Boolean? = null,
    var paymentMethod: String? = null,
) : Serializable
