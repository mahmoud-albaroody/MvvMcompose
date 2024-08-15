package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang

data class Lookup(
    val id: Int,
    val nameAr: String,
    val nameEn: String
) {
    fun getName(): String {
        return if (lang == "en") {
            nameEn
        } else {
            nameAr
        }
    }
}