package com.bitaqaty.reseller.data.repository

import android.util.Log
import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.DataResult
import com.bitaqaty.reseller.data.model.ForgetPassword
import com.bitaqaty.reseller.data.model.ForgetPasswordSend
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.RemainingTrials
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.data.model.ResetAccessData
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.ValidateResetAccessData
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import javax.inject.Inject


class BBRepository @Inject constructor(
    private val apiService: ApiService
) : BBRepositoryInterface {


    override suspend fun signIn(jsonObject: JsonObject):
            Flow<DataResult> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.signIn(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

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

    override suspend fun generateHomeSalesReport(jsonObject: JsonObject): Flow<ReportLog> = flow {
        //   emit(DataState.Loading)
        val logoutResult = apiService.generateHomeSalesReport(jsonObject)
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

    override suspend fun loginChangePassword(jsonObject: JsonObject):
            Flow<DataResult> =
        flow {
            //   emit(DataState.Loading)

            val searchResult = apiService.loginChangePassword(jsonObject)
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


    override suspend fun getTransactionLogList(jsonObject: JsonObject):
            Flow<TransactionLogResult> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.getTransactionLogList(jsonObject)
            emit(searchResult)
            Log.e("sssdd", "adsasd")

        }


    override suspend fun getSettlementRequest(jsonObject: JsonObject):
            Flow<SettlementResponse> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.getSettlementRequest(jsonObject)
            emit(searchResult)
        }

    override suspend fun getRechargeLogRequest(jsonObject: JsonObject):
            Flow<RechargingLogResult> =
        flow {
            //   emit(DataState.Loading)
            val searchResult = apiService.getRechargingLogList(jsonObject)
            emit(searchResult)
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