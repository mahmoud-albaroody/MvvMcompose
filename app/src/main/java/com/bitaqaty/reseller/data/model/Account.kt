package com.bitaqaty.reseller.data.model

import com.bitaqaty.reseller.utilities.Globals.lang

data class Account(
    val oneCardAccountNameAr: String? = null,
    val oneCardAccountNameEn: String? = null,
    val oneCardAccountNumber: String? = null,
    val oneCardBankAccountCurrencyAr: String? = null,
    val oneCardBankAccountCurrencyEn: String? = null,
    val oneCardBankAccountId: String? = null,
    val oneCardBankAddresssAr: String? = null,
    val oneCardBankAddresssEn: String? = null,
    val oneCardBankIban: String? = null,
    val oneCardBankLogoPath: String? = null,
    val oneCardBankNameAr: String? = null,
    val oneCardBankNameEn: String? = null,
    val refNumberIsMandatory: Boolean? = null,
) {
    fun getBankName(): String {
        return if (lang == "en") {
            oneCardBankNameEn ?: ""
        } else {
            oneCardBankNameAr ?: ""
        }
    }

    fun getAccountNumber(): String {
        return oneCardAccountNumber ?: ""
    }

    fun getAccountName(): String {
        return if (lang == "en") {
            oneCardAccountNameEn ?: ""
        } else {
            oneCardAccountNameAr ?: ""
        }
    }

    fun getAccountAddress(): String {
        return if (lang == "en") {
            oneCardBankAddresssEn ?: ""
        } else {
            oneCardBankAddresssAr ?: ""
        }
    }

    fun getIban(): String {
        return oneCardBankIban ?: ""
    }

    fun getCurrency(): String {
        return if (lang == "en") {
            oneCardBankAccountCurrencyEn ?: ""
        } else {
            oneCardBankAccountCurrencyAr ?: ""
        }
    }
}