package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.ChildMerchantRequest
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ErrorMessage
import com.bitaqaty.reseller.data.model.ForgetPassword
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.PaymentStatus
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.PurchaseRequest
import com.bitaqaty.reseller.data.model.PurchaseResponse
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
import com.bitaqaty.reseller.data.model.SettlementRequestDataRequest
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.model.StatusResponse
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.data.model.ValidationSurpayChargeResult
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST(ApiURL.GET_CATEGORIES)
    suspend fun getCategoryList(): ArrayList<Category>
    @POST(ApiURL.SUB_ACCOUNT_HOME)
    suspend fun getTopMerchants(): TopMerchants
    @POST(ApiURL.SUB_ACCOUNT_HOME_CHILD)
    suspend fun getChildMerchants(@Body childMerchantRequest: ChildMerchantRequest): TopChildMerchant
    @POST(ApiURL.GET_MERCHANTS)
    suspend fun getMerchants(@Path("category_id") categoryId: Int): ArrayList<Merchant>
    @POST(ApiURL.GET_PRODUCT_LIST)
    suspend fun getProductList(@Body productsInfo: ProductListRequest): ProductListResponse
    @POST(ApiURL.CATEGORY_EDIT)
    suspend fun editCategory(
        @Path("currentCategoryId") currentCategoryId: Int,
        @Path("newCategoryId") newCategoryId: Int
    ): Unit
    @POST(ApiURL.SYSTEM_SETTINGS)
    suspend fun getSystemSetting(): ArrayList<SystemSettings>
    @POST(ApiURL.GET_SETTLEMENT_REQUEST_DATA)
    suspend fun getSettlementRequestData(): PersonalBankData
    @POST(ApiURL.CREATE_SETTLEMENT_REQUEST)
    suspend fun createSettlementRequest(@Body settlementRequest: SettlementRequestDataRequest): SettlementRequestResult
    @POST(ApiURL.PROFILE)
    suspend fun getProfile(): User
    @POST(ApiURL.PURCHASE_ORDER)
    suspend fun purchaseOrder(@Body products: PurchaseRequest): PurchaseResponse
    @POST(ApiURL.GET_TRANSACTIONS_LIST)
    suspend fun getTransactionLogList(@Body transactionRequestBody: TransactionRequestBody):
            Response<TransactionLogResult>

    @POST(ApiURL.GET_SETTLEMENT_REQUEST)
    suspend fun getSettlementRequest(@Body jsonObject: JsonObject):
            Response<SettlementResponse>


    @POST(ApiURL.CHARGING_LOG_LIST)
    suspend fun getRechargingLogList(@Body jsonObject: JsonObject): Response<RechargingLogResult>

    @POST(ApiURL.SIGN_IN)
    suspend fun signIn(@Body jsonObject: JsonObject): Response<DataResult>

    @POST(ApiURL.LOGIN_CHANGE_PASSWORD)
    suspend fun loginChangePassword(@Body jsonObject: JsonObject): Response<DataResult>

    @POST(ApiURL.RESET_CHANGE_PASSWORD)
    suspend fun resetChangePassword(@Body jsonObject: JsonObject): DataResult

    @POST(ApiURL.VERIFY_FORGET_PASSWORD)
    suspend fun verifyForgetPassword(@Body jsonObject: JsonObject): Response<DataResult>

    @POST(ApiURL.FORGET_PASSWORD_SEND)
    suspend fun forgetPasswordSend(@Body jsonObject: JsonObject): ForgetPasswordSend

    @POST(ApiURL.FORGET_PASSWORD_INIT)
    suspend fun forgetPassword(@Body jsonObject: JsonObject): ForgetPassword


    @POST(ApiURL.CHANGE_PASSWORD)
    suspend fun changePassword(@Body jsonObject: JsonObject): DataResult

    @POST(ApiURL.ACCESS_DATA)
    suspend fun resetAccessData(@Body jsonObject: JsonObject): Response<ResetAccessData>


    @POST(ApiURL.REMAINING_TRAILS)
    suspend fun getRemainingTrials(@Body jsonObject: JsonObject): RemainingTrials

    @POST(ApiURL.VERIFICATION_REMAINING_TRAILS)
    suspend fun getVerificationRemainingTrials(@Body jsonObject: JsonObject): Response<RemainingTrials>

    @POST(ApiURL.RESEND_SMS_VERIFICATION_CODE)
    suspend fun resendResetAccessDataSms(@Body jsonObject: JsonObject): Response<StatusResponse>

    @POST(ApiURL.AUTHENTICATED_LOGIN)
    suspend fun authenticatedLogin(@Body jsonObject: JsonObject): Response<User>



    @POST(ApiURL.SYSTEM_SETTINGS)
    suspend fun getSystemSettings(): ArrayList<SystemSettings>

    @POST(ApiURL.VALIDATE_SMS_VERIFICATION_CODE)
    suspend fun validateVerificationCode(@Body jsonObject: JsonObject): Response<User>

    @POST(ApiURL.VALIDATE_RESET_SMS_VERIFICATION_CODE)
    suspend fun validateResetVerificationCode(@Body jsonObject: JsonObject):
            Response<ValidateResetAccessData>


    @POST(ApiURL.GET_PRODUCT_LIST)
    suspend fun getProductList(@Body jsonObject: JsonObject): ProductListResult

    @POST(ApiURL.GENERATE_SALES_REPORT)
    suspend fun generateHomeSalesReport(@Body reportRequestBody: ReportRequestBody): ReportLog

    @POST(ApiURL.GET_CATEGORY_LIST)
    suspend fun getSimpleCategoryList(): ArrayList<Category>

    @POST(ApiURL.GET_MERCHANT_LIST)
    suspend fun getSimpleMerchantList(@Path("id") categoryId: Int): ArrayList<Merchant>

    @POST(ApiURL.GET_PRODUCT_LOOKUP)
    suspend fun getProductLookList(@Body jsonObject: JsonObject): ArrayList<Product>

    @POST(ApiURL.SUREPAY_RESELLER_RECHARGE_METHODS)
    suspend fun getSurePayRechargeMethods(): ArrayList<RechargeMethod>


    @POST(ApiURL.GET_USERNAMES_LIST)
    suspend fun getUserNamesList(): ArrayList<LogUserName>

    @POST(ApiURL.SEARCH_BANK_TRANSFER_REQUEST)
    suspend fun searchBankTransfer(@Body body: RequestBankTransferLogBody): SearchBank

    @POST(ApiURL.ONE_CARD_COUNTRIES)
    suspend fun onecardCountries(): AccountsCountries

    @POST(ApiURL.ONE_CARD_ACCOUNTS)
    suspend fun onecardAccount(@Body requestOneCardAccountsBody: RequestOneCardAccountsBody): AccountsByCountry

    @POST(ApiURL.SENDER_COUNTRIES)
    suspend fun senderCounters(): ArrayList<AccountsCountries>

    @POST(ApiURL.SAVED_ACCOUNTS)
    suspend fun saveAccount(): ArrayList<SavedAccount>


    @POST(ApiURL.SENDER_ACCOUNTS_BY_COUNTRY)
    suspend fun senderAccountByCounter(@Path("id") id: String): ArrayList<AccountsCountries>

    @POST(ApiURL.VALIDATE_SUREPAY_CHARGING)
    suspend fun validateSurePayCharging(@Body jsonObject: JsonObject): ValidationSurpayChargeResult

    @POST(ApiURL.SUREPAY_CHARGING)
    suspend fun surePayCharging(@Body jsonObject: JsonObject): PaymentStatus


    @POST(ApiURL.LOGOUT)
    suspend fun logout(): Response<ErrorMessage>

}