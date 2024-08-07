package com.bitaqaty.reseller.data.model

import java.io.Serializable

data class LogUserName(
    var id: Int = 0,
    private var fullName: String? = null,
    private var userName: String? = null
) : Serializable {
    fun getFullName(): String {
        return fullName ?: ""
    }

    fun getUserName(): String {
        return userName ?: ""
    }
}
