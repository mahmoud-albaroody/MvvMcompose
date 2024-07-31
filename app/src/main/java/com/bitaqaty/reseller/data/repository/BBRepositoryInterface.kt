package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPassword
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface BBRepositoryInterface {
    suspend fun getTransactionLogList(jsonObject: JsonObject): Flow<TransactionLogResult>
    suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>>
    suspend fun getSettlementRequest(jsonObject: JsonObject): Flow<SettlementResponse>
    suspend fun getRechargeLogRequest(jsonObject: JsonObject): Flow<RechargingLogResult>
    suspend fun signIn(jsonObject: JsonObject): Flow<DataResult>
    suspend fun loginChangePassword(jsonObject: JsonObject): Flow<DataResult>
    suspend fun resetChangePassword(jsonObject: JsonObject): Flow<DataResult>
    suspend fun verifyForgetPassword(jsonObject: JsonObject): Flow<DataResult>

    suspend fun forgetPasswordSend(jsonObject: JsonObject): Flow<ForgetPasswordSend>

    suspend fun changePassword(jsonObject: JsonObject): Flow<DataResult>
    suspend fun getRemainingTrials(jsonObject: JsonObject): Flow<RemainingTrials>
    suspend fun getVerificationRemainingTrials(jsonObject: JsonObject): Flow<RemainingTrials>

    suspend fun resendResetAccessDataSms(jsonObject: JsonObject): Flow<Void>

    suspend fun authenticatedLogin(jsonObject: JsonObject): Flow<User>

    suspend fun getProfile(): Flow<User>

    suspend fun validateVerificationCode(jsonObject: JsonObject): Flow<User>

    suspend fun validateResetVerificationCode(jsonObject: JsonObject): Flow<ValidateResetAccessData>

    suspend fun resetAccessData(jsonObject: JsonObject): Flow<ResetAccessData>
    suspend fun forgetPassword(jsonObject: JsonObject): Flow<ForgetPassword>

    suspend fun getProductList(jsonObject: JsonObject): Flow<ProductListResult>


    suspend fun systemSettings(): Flow<ArrayList<SystemSettings>>
    suspend fun logout(): Flow<Void>

    suspend fun generateHomeSalesReport(jsonObject: JsonObject): Flow<ReportLog>


}