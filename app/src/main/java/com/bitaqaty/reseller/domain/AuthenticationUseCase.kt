package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun signIn(jsonObject: JsonObject) = repo.signIn(jsonObject)
    suspend fun loginChangePassword(jsonObject: JsonObject) = repo.loginChangePassword(jsonObject)
    suspend fun resetChangePassword(jsonObject: JsonObject) = repo.resetChangePassword(jsonObject)
    suspend fun verifyForgetPassword(jsonObject: JsonObject) = repo.verifyForgetPassword(jsonObject)
    suspend fun changePassword(jsonObject: JsonObject) = repo.changePassword(jsonObject)
    suspend fun getRemainingTrials(jsonObject: JsonObject) = repo.getRemainingTrials(jsonObject)
    suspend fun getVerificationRemainingTrials(jsonObject: JsonObject) =
        repo.getVerificationRemainingTrials(jsonObject)
    suspend fun resendResetAccessDataSms(jsonObject: JsonObject) =
        repo.resendResetAccessDataSms(jsonObject)
    suspend fun resetAccessData(jsonObject: JsonObject) =
        repo.resetAccessData(jsonObject)

    suspend fun forgetPassword(jsonObject: JsonObject) =
        repo.forgetPassword(jsonObject)
    suspend fun forgetPasswordSend(jsonObject: JsonObject) =
        repo.forgetPasswordSend(jsonObject)

    suspend fun authenticatedLogin(jsonObject: JsonObject) = repo.authenticatedLogin(jsonObject)

    suspend fun validateVerificationCode(jsonObject: JsonObject) =
        repo.validateVerificationCode(jsonObject)
    suspend fun validateResetVerificationCode(jsonObject: JsonObject) =
        repo.validateResetVerificationCode(jsonObject)



    suspend fun logout() = repo.logout()
}