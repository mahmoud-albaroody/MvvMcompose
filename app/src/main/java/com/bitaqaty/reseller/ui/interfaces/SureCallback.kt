package com.bitaqaty.reseller.ui.interfaces

import com.bitaqaty.reseller.data.model.MadaResponse

fun interface SureCallback {
    fun sureCallback(response: MadaResponse?)
}