package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class RequestOneCardAccountsBody(
    var resellerUsername: String? = null,
    var countryId: Int? = null,
    var pageSize: Int = 1000,
    var pageNumber: Int = 1
) : Serializable
