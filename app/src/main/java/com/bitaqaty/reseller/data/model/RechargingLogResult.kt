package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class RechargingLogResult(
    var errors: List<ErrorMessage> = ArrayList(),
    var resultList: ArrayList<RechargingLog> = ArrayList(),
    var totalElementsCount: Int = 0
) : Serializable
