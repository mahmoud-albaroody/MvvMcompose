package com.bitaqaty.reseller.data.model

import com.google.gson.annotations.SerializedName

open class StatusResponse(
    @SerializedName("status")
    val status: Int = 0,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("errors")
    var errors: ArrayList<ErrorMessage> = ArrayList(),
)


