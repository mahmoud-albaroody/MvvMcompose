package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class SubPermission (
    val id : Int = 0,
    val name : String = "",
    val nameAr : String = "",
    val description : String = "",
    val orderId : Int = 0,
    var enabled : Boolean = false,
    var childPermssions: ArrayList<SubPermission>  = ArrayList()
): Serializable


