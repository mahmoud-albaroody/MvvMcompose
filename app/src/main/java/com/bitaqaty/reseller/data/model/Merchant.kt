package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals

data class Merchant(
    val id: Int = 0,
    val nameAr: String? = null,
    val nameEn: String? = null,
    val descriptionAr: String? = null,
    val descriptionEn: String? = null,
    val howToUseAr: String? = null,
    val howToUseEn: String? = null,
    val logoPath: String? = null,
    val pinned: Boolean = true,
    val isDelete: Boolean = false
){
    fun getName(): String {
        return if (Globals.lang == "en") {
            nameEn ?: nameAr ?: ""
        } else {
            nameAr ?: nameEn ?: ""
        }
    }

    fun getDescription(): String {
        return if (Globals.lang == "en") {
            descriptionEn ?: descriptionAr ?: ""
        } else {
            descriptionAr ?: descriptionEn ?: ""
        }
    }

    fun getHowToUse(): String {
        return if (Globals.lang == "en") {
            howToUseEn ?: howToUseAr ?: ""
        } else {
            howToUseAr ?: howToUseEn ?: ""
        }
    }
}
