package com.bitaqaty.reseller.data.model


data class MadaResponse(
    var result:String?=null,
    var signature: String?=null,
    var madaResponseModel: MadaResponseModel?=null,
    val TID : String?=null,
    val termType : String?=null,
    val hwSerial : String?=null,
    val MNC : String?=null,
    val appVersion:String?=null
)
