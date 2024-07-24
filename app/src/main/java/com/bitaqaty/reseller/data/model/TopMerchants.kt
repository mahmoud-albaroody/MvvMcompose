package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class TopMerchants(
    val errors: ArrayList<ErrorMessage>? = null,
    val merchants: ArrayList<TopMerchant>? = null
)

data class TopMerchant(
    var id: Int? = null,
    var nameAr: String? = null,
    var nameEn: String? = null,
    var logoPath: String? = null,
    var merchantId: Int? = null,
    var categoryId: Int? = null,
    var defaultHomeMerchant: Boolean? = null,
    var displayOrder: Int? = null,
    var deleted: Boolean = false,
    var updated: Boolean? = null,
    var category: Boolean? = null,
    var descriptionAr: String? = null,
    var descriptionEn: String? = null,
    var howToUseAr: String? = null,
    var howToUseEn: String? = null,
) : Serializable {
    fun currentId(): Int {
        return if (category == true) {
            categoryId ?: merchantId ?: 0
        } else {
            merchantId ?: categoryId ?: 0
        }
    }
}
