package com.bitaqaty.reseller.data.model

data class SavedAccounts(
    var errors: ArrayList<ErrorMessage>? = ArrayList(),
    var savedAccounts: ArrayList<SavedAccount>? = null
)