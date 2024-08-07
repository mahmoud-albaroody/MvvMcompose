package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals
import java.io.Serializable

data class RechargeMethod(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    val id: Int = 0,
    var nameEn: String? = "",
    private var nameAr: String? = "",
    private var chargingCode: String? = "",
    val bitaqatyBusinessPermission: SubPermission? = null,
    var discriminatorValue: String? = null,
    var discriminatorValuel: String? = null,
    var displayOrder: Int = 0,
    var enabled: Boolean = true,
    var isSurepayRechargingMethod: Boolean? = null,
    var isSurepay: Boolean = false
) : Serializable {

    fun getName(): String {
        return if (Globals.lang == "en") {
            nameEn ?: nameAr ?: ""
        } else {
            nameAr ?: nameEn ?: ""
        }
    }

    fun setName(name: String) {
        if (Globals.lang == "en") {
            nameEn = name
        } else {
            nameAr = name
        }
    }

    fun getDiscriminatorValuell(): String {
        return if (discriminatorValue == null) {
            discriminatorValuel ?: ""
        } else {
            discriminatorValue ?: ""
        }
    }

    fun getChargingMCode(): String {
        return if (isSurepayRechargingMethod != null) {
            if (isSurepay && enabled && isSurepayRechargingMethod as Boolean) {
                if (chargingCode == "null") {
                    ""
                } else {
                    chargingCode ?: ""
                }
            } else {
                ""
            }
        } else {
            chargingCode ?: ""
        }
    }
}
