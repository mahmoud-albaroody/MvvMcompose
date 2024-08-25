package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.RechargingLogResult
import com.bitaqaty.reseller.data.model.SettlementResponse
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SettlementUseCase @Inject constructor(private val repo: BBRepository) {

    suspend fun settlement(jsonObject: JsonObject):
            Flow<Resource<SettlementResponse>> {
        return flow {
            emit(
                repo.getSettlementRequest(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }
}