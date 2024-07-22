package com.bitaqaty.reseller.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("nameAr")
    val nameAr: String? = null,
    @SerializedName("nameEn")
    val nameEn: String? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null,
    @SerializedName("descriptionAr")
    val descriptionAr: String? = null,
    @SerializedName("descriptionEn")
    val descriptionEn: String? = null,
    @SerializedName("logoPath")
    val logoPath: String? = null,
)
