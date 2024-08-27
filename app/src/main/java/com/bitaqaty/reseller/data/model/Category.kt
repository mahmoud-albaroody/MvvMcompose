package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("nameAr")
    private val nameAr: String? = null,
    @SerializedName("nameEn")
    private val nameEn: String? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null,
    @SerializedName("descriptionAr")
    val descriptionAr: String? = null,
    @SerializedName("descriptionEn")
    val descriptionEn: String? = null,
    @SerializedName("logoPath")
    val logoPath: String? = null,
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
}
