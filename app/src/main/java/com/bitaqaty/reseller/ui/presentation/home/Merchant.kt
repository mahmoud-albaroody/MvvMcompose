package com.bitaqaty.reseller.ui.presentation.home

import com.bitaqaty.reseller.utilities.Globals.lang

data class Merchant(
    val nameAr: String,
    val nameEn: String,
    val imageUrl: String,
    var id: Int = 0
){
    fun getName():String{
        return if(lang=="ar"){
            nameAr
        }else{
            nameEn
        }
    }
}
