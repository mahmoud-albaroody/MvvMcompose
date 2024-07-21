package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Utils

data class User(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    var token: String? = null,
    var oneSingleToken: String? = null,
    var reseller: UserInfo? = null,
    var accountType: String? = null,
    var authenticationType: String? = null,
    var accountManagerDTO: AccountManager? = null
)
object CurrentUser {
    private var user: User? = null

    fun getInstance(): User? {
        if (user == null) {
            synchronized(this) {
                user = Utils.getUserData()
            }
        }
        return user
    }

    fun saveInstance(userData: User?) {
        user = userData
    }
}