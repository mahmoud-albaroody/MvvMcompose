package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class RechargeLogUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun rechargeLog(jsonObject: JsonObject):
            Flow<Resource<RechargingLogResult>> {
        return flow {
            emit(
                repo.getRechargeLogRequest(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }
}