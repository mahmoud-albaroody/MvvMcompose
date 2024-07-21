package com.bitaqaty.reseller.ui.delegate


import com.bitaqaty.reseller.data.model.MadaResponse


interface GetLastStatusCallback {
    fun onLastStatusReceived(madaResponse: MadaResponse?)
}