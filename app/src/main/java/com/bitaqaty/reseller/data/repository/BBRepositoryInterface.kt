package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.model.AccountsByCountry
import com.bitaqaty.reseller.data.model.AccountsCountries
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ErrorMessage
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
import com.bitaqaty.reseller.data.model.StatusResponse
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.ChildMerchantRequest
import com.bitaqaty.reseller.data.model.FavoriteRequest
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.PurchaseRequest
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.model.SettlementRequestDataRequest
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.data.model.ValidationSurpayChargeResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface BBRepositoryInterface {
    suspend fun getTransactionLogList(transactionRequestBody: TransactionRequestBody):
            Resource<TransactionLogResult>

    suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>>
    suspend fun getTopMerchants(): Flow<DataState<TopMerchants>>
    suspend fun getChildMerchants(childMerchantRequest: ChildMerchantRequest): Flow<DataState<TopChildMerchant>>
    suspend fun getMerchants(categoryId: Int): Flow<DataState<ArrayList<Merchant>>>
    suspend fun getProducts(productsInfo: ProductListRequest): Flow<DataState<ProductListResponse>>
    suspend fun editCategory(currentCategoryId: Int, newCategoryId: Int): Flow<DataState<Unit>>
    suspend fun getSystemSettings(): Flow<DataState<ArrayList<SystemSettings>>>
    suspend fun getSettlementRequestData(): Flow<DataState<PersonalBankData>>
    suspend fun createSettlementRequest(settlementRequest: SettlementRequestDataRequest): Flow<DataState<SettlementRequestResult>>
    suspend fun getProfile(): Flow<DataState<User>>
    suspend fun purchaseOrder(products: PurchaseRequest): Flow<DataState<PurchaseResponse>>

    suspend fun getSettlementRequest(jsonObject: JsonObject): Resource<SettlementResponse>
    suspend fun getRechargeLogRequest(jsonObject: JsonObject): Resource<RechargingLogResult>
    suspend fun signIn(jsonObject: JsonObject): Resource<DataResult>
    suspend fun loginChangePassword(jsonObject: JsonObject): Resource<DataResult>
    suspend fun resetChangePassword(jsonObject: JsonObject): Flow<DataResult>
    suspend fun verifyForgetPassword(jsonObject: JsonObject): Resource<DataResult>

    suspend fun forgetPasswordSend(jsonObject: JsonObject): Flow<ForgetPasswordSend>

    suspend fun changePassword(jsonObject: JsonObject): Flow<DataResult>
    suspend fun getRemainingTrials(jsonObject: JsonObject): Flow<RemainingTrials>
    suspend fun getVerificationRemainingTrials(jsonObject: JsonObject): Resource<RemainingTrials>

    suspend fun resendResetAccessDataSms(jsonObject: JsonObject): Resource<StatusResponse>

    suspend fun authenticatedLogin(jsonObject: JsonObject): Resource<User>


    suspend fun validateVerificationCode(jsonObject: JsonObject): Resource<User>

    suspend fun validateResetVerificationCode(jsonObject: JsonObject): Resource<ValidateResetAccessData>

    suspend fun resetAccessData(jsonObject: JsonObject): Resource<ResetAccessData>
    suspend fun forgetPassword(jsonObject: JsonObject): Flow<ForgetPassword>

    suspend fun getProductList(jsonObject: JsonObject): Flow<ProductListResult>


    suspend fun systemSettings(): Flow<ArrayList<SystemSettings>>
    suspend fun logout(): Resource<ErrorMessage>

    suspend fun generateHomeSalesReport(reportRequestBody: ReportRequestBody): Flow<ReportLog>

    suspend fun getSimpleCategoryList(): Flow<ArrayList<Category>>
    suspend fun getSimpleMerchantList(categoryId: Int): Flow<DataState<ArrayList<Merchant>>>
    suspend fun getProductLookList(jsonObject: JsonObject): Flow<ArrayList<Product>>

    suspend fun getSurePayRechargeMethods(): Flow<ArrayList<RechargeMethod>>
    suspend fun getUserNamesList(): Flow<ArrayList<LogUserName>>


    suspend fun searchBankTransfer(bankTransferLogBody: RequestBankTransferLogBody): Flow<SearchBank>
    suspend fun onecardCountries(): Flow<AccountsCountries>
    suspend fun onecardAccount(requestOneCardAccountsBody: RequestOneCardAccountsBody): Flow<AccountsByCountry>
    suspend fun senderCounters(): Flow<ArrayList<AccountsCountries>>

    suspend fun saveAccount(): Flow<ArrayList<SavedAccount>>
    suspend fun senderAccountByCounter(id: String): Flow<ArrayList<AccountsCountries>>

    suspend fun validateSurePayCharging(jsonObject: JsonObject): Flow<ValidationSurpayChargeResult>

    suspend fun surePayCharging(jsonObject: JsonObject): Flow<PaymentStatus>
    suspend fun addFavoriteProduct(favoriteProduct: FavoriteRequest): Flow<DataState<Unit>>
    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteRequest): Flow<DataState<Unit>>
    suspend fun getFavoriteProducts(): Flow<DataState<ProductListResult>>

}