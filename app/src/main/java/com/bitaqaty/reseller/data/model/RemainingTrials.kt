package com.bitaqaty.reseller.data.model

import java.util.ArrayList

data class RemainingTrials(

    private var allowedSmsTrials: Int? = null,
    private var remainingInvalidSmsTrials: Int? = null,
    private var waitingSecondsToResendSms: Int? = null,
    private var showResendSmsLinkAfter: Long? = null,
    private var resendSmsTrials: Int? = null,
    private var showResendSMSLinkMinutes: Double? = null,
    private var remainingResendSmsTrials: Int? = null,
    private var forgetPasswordRemainingInvalidSmsTrials: Int? = null,
    private var waitingSecondsToResendForgetPasswordSms: Int? = null,
    private var forgetPasswordRemainingInvalidMailTrials: Int? = null,
    private var forgetPasswordResendSmsTrials: Int? = null,
    private var remainingForgetPasswordSmsResendTrials: Int? = null,
    private var remainingResendResetAccessDataSMSTrails: Int? = null,
    private var remainingInvalidResetAccessDataSMSTrails: Int? = null,
    override var errors: ArrayList<ErrorMessage>?=null
):StatusResponse {
    fun getAllowedSmsTrials(): Int {
        return allowedSmsTrials ?: 0
    }

    fun getRemainingInvalidSmsTrials(): Int {
        return remainingInvalidSmsTrials
            ?: remainingInvalidResetAccessDataSMSTrails
            ?: forgetPasswordRemainingInvalidSmsTrials
            ?: 0
    }

    fun getWaitingSecondsToResendSms(): Int {
        return waitingSecondsToResendSms
            ?: waitingSecondsToResendForgetPasswordSms
            ?: 0
    }

    fun getResendSmsTrials(): Int {
        return resendSmsTrials
            ?: forgetPasswordResendSmsTrials
            ?: 0
    }

    fun getRemainingResendSmsTrial(): Int {
        return remainingResendSmsTrials
            ?: remainingResendResetAccessDataSMSTrails
            ?: remainingForgetPasswordSmsResendTrials
            ?: 0
    }

    fun getWaitTime(): Double {
        return showResendSMSLinkMinutes ?: 1.0
    }
}
