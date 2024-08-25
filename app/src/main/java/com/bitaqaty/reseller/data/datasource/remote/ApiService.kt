package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPassword
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.PaymentStatus
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.data.model.ReportRequestBody
import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestOneCardAccountsBody
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.SavedAccount
import com.bitaqaty.reseller.data.model.SearchBank
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.data.model.ValidationSurpayChargeResult
import com.bitaqaty.reseller.data.model.artist.ArtistDetail
import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import retrofit2.Response
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
    suspend fun getTransactionLogList(@Body transactionRequestBody: TransactionRequestBody):
            Response<TransactionLogResult>

    @POST(Globals.GET_SETTLEMENT_REQUEST)
    suspend fun getSettlementRequest(@Body jsonObject: JsonObject):
            Response<SettlementResponse>


    @POST(Globals.CHARGING_LOG_LIST)
    suspend fun getRechargingLogList(@Body jsonObject: JsonObject): Response<RechargingLogResult>

    @POST(Globals.SIGN_IN)
    suspend fun signIn(@Body jsonObject: JsonObject): Response<DataResult>

    @POST(Globals.LOGIN_CHANGE_PASSWORD)
    suspend fun loginChangePassword(@Body jsonObject: JsonObject): Response<DataResult>

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
    suspend fun generateHomeSalesReport(@Body reportRequestBody: ReportRequestBody): ReportLog

    @POST(Globals.GET_CATEGORY_LIST)
    suspend fun getSimpleCategoryList(): ArrayList<Category>

    @POST(Globals.GET_MERCHANT_LIST)
    suspend fun getSimpleMerchantList(@Path("id") categoryId: Int): ArrayList<Merchant>

    @POST(Globals.GET_PRODUCT_LOOKUP)
    suspend fun getProductLookList(@Body jsonObject: JsonObject): ArrayList<Product>

    @POST(Globals.SUREPAY_RESELLER_RECHARGE_METHODS)
    suspend fun getSurePayRechargeMethods(): ArrayList<RechargeMethod>

    @POST(Globals.GET_MERCHANTS)
    suspend fun getMerchants(@Path("category_id") categoryId: Int): ArrayList<Merchant>

    @POST(Globals.GET_USERNAMES_LIST)
    suspend fun getUserNamesList(): ArrayList<LogUserName>

    @POST(Globals.SEARCH_BANK_TRANSFER_REQUEST)
    suspend fun searchBankTransfer(@Body body: RequestBankTransferLogBody): SearchBank

    @POST(Globals.ONE_CARD_COUNTRIES)
    suspend fun onecardCountries(): AccountsCountries

    @POST(Globals.ONE_CARD_ACCOUNTS)
    suspend fun onecardAccount(@Body requestOneCardAccountsBody: RequestOneCardAccountsBody): AccountsByCountry

    @POST(Globals.SENDER_COUNTRIES)
    suspend fun senderCounters(): ArrayList<AccountsCountries>

    @POST(Globals.SAVED_ACCOUNTS)
    suspend fun saveAccount(): ArrayList<SavedAccount>


    @POST(Globals.SENDER_ACCOUNTS_BY_COUNTRY)
    suspend fun senderAccountByCounter(@Path("id") id: String): ArrayList<AccountsCountries>

    @POST(Globals.VALIDATE_SUREPAY_CHARGING)
    suspend fun validateSurePayCharging(@Body jsonObject: JsonObject): ValidationSurpayChargeResult

    @POST(Globals.SUREPAY_CHARGING)
    suspend fun surePayCharging(@Body jsonObject: JsonObject): PaymentStatus

}