package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.PaymentStatus
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RechargeUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun validateSurePayCharging(jsonObject: JsonObject) =
        repo.validateSurePayCharging(jsonObject)

    suspend fun partnerRecharge(jsonObject: JsonObject): Flow<Resource<PaymentStatus>> {
        return flow {
            emit(repo.surePayCharging(jsonObject))
        }.flowOn(Dispatchers.IO)
    }

}
