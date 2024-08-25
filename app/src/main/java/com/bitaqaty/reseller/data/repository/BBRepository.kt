package com.bitaqaty.reseller.data.repository

import android.provider.SyncStateContract
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.datasource.remote.paging.TransactionPagingDataSource
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
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject


class BBRepository @Inject constructor(
    private val apiService: ApiService,
) : BBRepositoryInterface {


    override suspend fun signIn(jsonObject: JsonObject):
            Resource<DataResult> {
        val response = apiService.signIn(jsonObject)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    override suspend fun loginChangePassword(jsonObject: JsonObject): Resource<DataResult> {
        val response = apiService.loginChangePassword(jsonObject)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    override suspend fun resetAccessData(jsonObject: JsonObject):
            Flow<ResetAccessData> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.resetAccessData(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }

    override suspend fun systemSettings():
            Flow<ArrayList<SystemSettings>> =
        flow {
            //   emit(DataState.Loading)
            val settingsResult = apiService.getSystemSettings()
            emit(settingsResult)

        }

    override suspend fun logout(): Flow<Void> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.logout()
        emit(logoutResult)

    }

    override suspend fun getProductList(jsonObject: JsonObject): Flow<ProductListResult> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getProductList(jsonObject)
        emit(logoutResult)

    }

    override suspend fun generateHomeSalesReport(reportRequestBody: ReportRequestBody): Flow<ReportLog> =
        flow {
            //   emit(DataState.Loading)
            val logoutResult = apiService.generateHomeSalesReport(reportRequestBody)
            emit(logoutResult)

        }

    override suspend fun getSimpleCategoryList(): Flow<ArrayList<Category>> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getSimpleCategoryList()
        emit(logoutResult)

    }

    override suspend fun getSimpleMerchantList(categoryId: Int): Flow<ArrayList<Merchant>> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getSimpleMerchantList(categoryId)
        emit(logoutResult)
    }

    override suspend fun getProductLookList(jsonObject: JsonObject): Flow<ArrayList<Product>> =
        flow {
            //   emit(DataState.Loading)
            val logoutResult = apiService.getProductLookList(jsonObject)
            emit(logoutResult)
        }

    override suspend fun getSurePayRechargeMethods(): Flow<ArrayList<RechargeMethod>> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getSurePayRechargeMethods()
        emit(logoutResult)
    }

    override suspend fun getMerchants(categoryId: Int): Flow<ArrayList<Merchant>> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getMerchants(categoryId)
        emit(logoutResult)
    }

    override suspend fun getUserNamesList(): Flow<ArrayList<LogUserName>> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.getUserNamesList()
        emit(logoutResult)
    }

    override suspend fun searchBankTransfer(bankTransferLogBody: RequestBankTransferLogBody): Flow<SearchBank> =
        flow {
            val searchBankTransfer = apiService.searchBankTransfer(bankTransferLogBody)
            emit(searchBankTransfer)
        }

    override suspend fun onecardCountries(): Flow<AccountsCountries> = flow {
        val searchBankTransfer = apiService.onecardCountries()
        emit(searchBankTransfer)
    }

    override suspend fun onecardAccount(requestOneCardAccountsBody: RequestOneCardAccountsBody): Flow<AccountsByCountry> =
        flow {
            val onecardAccount = apiService.onecardAccount(requestOneCardAccountsBody)
            emit(onecardAccount)
        }

    override suspend fun senderCounters(): Flow<ArrayList<AccountsCountries>> = flow {
        val senderCounters = apiService.senderCounters()
        emit(senderCounters)
    }

    override suspend fun saveAccount(): Flow<ArrayList<SavedAccount>> = flow {
        val saveAccount = apiService.saveAccount()
        emit(saveAccount)
    }

    override suspend fun senderAccountByCounter(id: String): Flow<ArrayList<AccountsCountries>> =
        flow {
            val senderAccountByCounter = apiService.senderAccountByCounter(id)
            emit(senderAccountByCounter)
        }

    override suspend fun validateSurePayCharging(jsonObject: JsonObject): Flow<ValidationSurpayChargeResult> =
        flow {
            val senderAccountByCounter = apiService.validateSurePayCharging(jsonObject)
            emit(senderAccountByCounter)
        }

    override suspend fun surePayCharging(jsonObject: JsonObject): Flow<PaymentStatus> =
        flow {
            val senderAccountByCounter = apiService.surePayCharging(jsonObject)
            emit(senderAccountByCounter)
        }


    override suspend fun forgetPassword(jsonObject: JsonObject):
            Flow<ForgetPassword> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.forgetPassword(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }

    override suspend fun forgetPasswordSend(jsonObject: JsonObject):
            Flow<ForgetPasswordSend> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.forgetPasswordSend(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun resetChangePassword(jsonObject: JsonObject): Flow<DataResult> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.resetChangePassword(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun verifyForgetPassword(jsonObject: JsonObject): Flow<DataResult> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.verifyForgetPassword(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun changePassword(jsonObject: JsonObject): Flow<DataResult> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.changePassword(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun getRemainingTrials(jsonObject: JsonObject): Flow<RemainingTrials> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.getRemainingTrials(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun getVerificationRemainingTrials(jsonObject: JsonObject): Flow<RemainingTrials> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.getVerificationRemainingTrials(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun resendResetAccessDataSms(jsonObject: JsonObject): Flow<Void> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.resendResetAccessDataSms(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun authenticatedLogin(jsonObject: JsonObject): Flow<User> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.authenticatedLogin(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun getProfile(): Flow<User> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.getProfile()
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun validateVerificationCode(jsonObject: JsonObject): Flow<User> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.validateVerificationCode(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun validateResetVerificationCode(jsonObject: JsonObject):
            Flow<ValidateResetAccessData> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.validateResetVerificationCode(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun getTransactionLogList(transactionRequestBody: TransactionRequestBody):
            Resource<TransactionLogResult> {
        val response = apiService.getTransactionLogList(transactionRequestBody)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }

    }


    override suspend fun getSettlementRequest(jsonObject: JsonObject):
            Resource<SettlementResponse> {
        val response = apiService.getSettlementRequest(jsonObject)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    override suspend fun getRechargeLogRequest(jsonObject: JsonObject):
            Resource<RechargingLogResult> {
        val response = apiService.getRechargingLogList(jsonObject)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }

    }


    override suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.getCategoryList()
                Log.e(
                    "dddd",
                    (searchResult as DataState.Success<ArrayList<Category>>).data.toString()
                )
                emit(searchResult)
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }


}