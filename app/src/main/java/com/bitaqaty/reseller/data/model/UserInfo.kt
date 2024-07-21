package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.Utils.fmt
import com.google.gson.Gson
import java.io.Serializable

data class UserInfo(
    var authenticationType: String = "",
    var id: Int = 0,
    var parentResellerId: Int = 0,
    var balance: Double = 0.0,
    var username: String = "",
    var confirmUsername: String = "",
    var customerStatus: String = "",
    var lastLoginDate: Long = 0,
    var fullName: String = "",
    var accountNumber: String = "",
    var email: String = "",
    var currencyEn: String = "",
    var currencyAr: String = "",
    var mobileNumber: String = "",
    var accessType: String = "",
    var subAccountDetailsDTO: SubAccountDetail? = null,
    var accountExpired: Boolean = false,
    var accountLocked: Boolean = false,
    var passwordExpired: Boolean = false,
    var active: Boolean = false,
    var acceptAgreement: Boolean = false,
    var permissions: ArrayList<SubPermission> = ArrayList(),
    var printResellerInfo: Boolean? = false,
    var parentResellerMobileNumber: String? = null,
    var parentResellerEmail: String? = null,
    var parentResellerFullName: String? = null,
    var resellerType: String? = null,
    var canSetSubResellerPrice: Boolean? = false,
    var canSetPriceLessThanCostPrice: Boolean = false,
    var baanNumber: String? = null,
    var showReceiptUrl: Boolean? = false,
    var showReceiptSupportNumbers: Boolean? = false,
    var subResellerChangeAmount: String? = null,
    var changeSubResellerBalanceType: String? = null,
    var enablePurchaseUsingCard: Boolean = false,
    var enablePurchaseUsingBalance: Boolean = false,
    var partner: Boolean = false,
    var showCart: Boolean = false
) : Serializable {

    fun getCurrentCurrency(): String {
        return if (lang == "en") {
            currencyEn
        } else {
            currencyAr
        }
    }

    fun getBalance(): String {
        return "${balance.fmt()} ${Utils.getUserCurrency()}"
    }

    override fun toString(): String {
        return "UserInfo(authenticationType='$authenticationType', id=$id, parentResellerId=$parentResellerId, balance=$balance, username='$username', confirmUsername='$confirmUsername', customerStatus='$customerStatus', lastLoginDate=$lastLoginDate, fullName='$fullName', accountNumber='$accountNumber', email='$email', currencyEn='$currencyEn', currencyAr='$currencyAr', mobileNumber='$mobileNumber', accessType='$accessType', subAccountDetailsDTO=$subAccountDetailsDTO, accountExpired=$accountExpired, accountLocked=$accountLocked, passwordExpired=$passwordExpired, active=$active, acceptAgreement=$acceptAgreement, permissions=$permissions, printResellerInfo=$printResellerInfo, parentResellerMobileNumber=$parentResellerMobileNumber, parentResellerEmail=$parentResellerEmail, parentResellerFullName=$parentResellerFullName, resellerType=$resellerType, canSetSubResellerPrice=$canSetSubResellerPrice, canSetPriceLessThanCostPrice=$canSetPriceLessThanCostPrice, baanNumber=$baanNumber, showReceiptUrl=$showReceiptUrl, showReceiptSupportNumbers=$showReceiptSupportNumbers, subResellerChangeAmount=$subResellerChangeAmount, changeSubResellerBalanceType=$changeSubResellerBalanceType, enablePurchaseUsingCard=$enablePurchaseUsingCard, enablePurchaseUsingBalance=$enablePurchaseUsingBalance, partner=$partner, showCart=$showCart)"
    }

    fun clone(): UserInfo {
        val stringAnimal = Gson().toJson(this, UserInfo::class.java)
        return Gson().fromJson(stringAnimal, UserInfo::class.java)
    }
}
