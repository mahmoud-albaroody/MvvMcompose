package com.bitaqaty.reseller.data.datasource.remote

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
import com.bitaqaty.reseller.data.model.artist.Artist
import com.bitaqaty.reseller.data.model.artist.ArtistDetail
import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST(ApiURL.GET_CATEGORIES)
    suspend fun getCategoryList():
            DataState<ArrayList<Category>>

    @POST(Globals.GET_TRANSACTIONS_LIST)
    suspend fun getTransactionLogList(@Body jsonObject: JsonObject):
            TransactionLogResult

    @POST(Globals.GET_SETTLEMENT_REQUEST)
    suspend fun getSettlementRequest(@Body jsonObject: JsonObject):
            SettlementResponse


    @POST(Globals.CHARGING_LOG_LIST)
    suspend fun getRechargingLogList(@Body jsonObject: JsonObject): RechargingLogResult

    @POST(Globals.SIGN_IN)
    suspend fun signIn(@Body jsonObject: JsonObject): DataResult

    @POST(Globals.LOGIN_CHANGE_PASSWORD)
    suspend fun loginChangePassword(@Body jsonObject: JsonObject): DataResult

    @POST(Globals.RESET_CHANGE_PASSWORD)
    suspend fun resetChangePassword(@Body jsonObject: JsonObject): DataResult

    @POST(Globals.VERIFY_FORGET_PASSWORD)
    suspend fun verifyForgetPassword(@Body jsonObject: JsonObject): DataResult

    @POST(Globals.FORGET_PASSWORD_SEND)
    suspend fun forgetPasswordSend(@Body jsonObject: JsonObject): ForgetPasswordSend

    @POST(Globals.FORGET_PASSWORD_INIT)
    suspend fun forgetPassword(@Body jsonObject: JsonObject): ForgetPassword


    @POST(Globals.CHANGE_PASSWORD)
    suspend fun changePassword(@Body jsonObject: JsonObject): DataResult

    @POST(Globals.ACCESS_DATA)
    suspend fun resetAccessData(@Body jsonObject: JsonObject): ResetAccessData


    @POST(Globals.REMAINING_TRAILS)
    suspend fun getRemainingTrials(@Body jsonObject: JsonObject): RemainingTrials

    @POST(Globals.VERIFICATION_REMAINING_TRAILS)
    suspend fun getVerificationRemainingTrials(@Body jsonObject: JsonObject): RemainingTrials

    @POST(Globals.RESEND_SMS_VERIFICATION_CODE)
    suspend fun resendResetAccessDataSms(@Body jsonObject: JsonObject): Void

    @POST(Globals.AUTHENTICATED_LOGIN)
    suspend fun authenticatedLogin(@Body jsonObject: JsonObject): User

    @POST(Globals.PROFILE)
    suspend fun getProfile(): User

    @POST(Globals.SYSTEM_SETTINGS)
    suspend fun getSystemSettings(): ArrayList<SystemSettings>

    @POST(Globals.VALIDATE_SMS_VERIFICATION_CODE)
    suspend fun validateVerificationCode(@Body jsonObject: JsonObject): User

    @POST(Globals.VALIDATE_RESET_SMS_VERIFICATION_CODE)
    suspend fun validateResetVerificationCode(@Body jsonObject: JsonObject): ValidateResetAccessData

    @POST(Globals.LOGOUT)
    suspend fun logout(): Void

    @POST(Globals.GET_PRODUCT_LIST)
    suspend fun getProductList(@Body jsonObject: JsonObject): ProductListResult

    @POST(Globals.GENERATE_SALES_REPORT)
    suspend fun generateHomeSalesReport(@Body jsonObject: JsonObject): ReportLog

}