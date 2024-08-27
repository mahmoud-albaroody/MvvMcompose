package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.StatusResponse
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData

import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val repo: BBRepository) {

    suspend fun signIn(jsonObject: JsonObject):
            Flow<Resource<DataResult>> {
        return flow {
            emit(
                repo.signIn(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginChangePassword(jsonObject: JsonObject) = repo.loginChangePassword(jsonObject)
    suspend fun resetChangePassword(jsonObject: JsonObject) = repo.resetChangePassword(jsonObject)
    suspend fun verifyForgetPassword(jsonObject: JsonObject):
            Flow<Resource<DataResult>> {
        return flow {
            emit(
                repo.verifyForgetPassword(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun changePassword(jsonObject: JsonObject) = repo.changePassword(jsonObject)
    suspend fun getRemainingTrials(jsonObject: JsonObject) = repo.getRemainingTrials(jsonObject)
    suspend fun getVerificationRemainingTrials(jsonObject: JsonObject):
            Flow<Resource<RemainingTrials>> {
        return flow {
            emit(
                repo.getVerificationRemainingTrials(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun resendResetAccessDataSms(jsonObject: JsonObject):
            Flow<Resource<StatusResponse>> {
        return flow {
            emit(
                repo.resendResetAccessDataSms(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun resetAccessData(jsonObject: JsonObject):
            Flow<Resource<ResetAccessData>> {
        return flow {
            emit(
                repo.resetAccessData(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun forgetPassword(jsonObject: JsonObject) =
        repo.forgetPassword(jsonObject)

    suspend fun forgetPasswordSend(jsonObject: JsonObject) =
        repo.forgetPasswordSend(jsonObject)

    suspend fun authenticatedLogin(jsonObject: JsonObject):
            Flow<Resource<User>> {
        return flow {
            emit(
                repo.authenticatedLogin(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun validateVerificationCode(jsonObject: JsonObject):
            Flow<Resource<User>> {
        return flow {
            emit(
                repo.validateVerificationCode(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun validateResetVerificationCode(jsonObject: JsonObject):
            Flow<Resource<ValidateResetAccessData>> {
        return flow {
            emit(
                repo.validateResetVerificationCode(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }


    suspend fun logout():
            Flow<Resource<ErrorMessage>> {
        return flow {
            emit(
                repo.logout()
            )
        }.flowOn(Dispatchers.IO)
    }
}