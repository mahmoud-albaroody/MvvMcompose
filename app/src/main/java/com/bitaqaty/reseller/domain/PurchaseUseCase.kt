package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.InitPurchaseResponse
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PurchaseUseCase @Inject constructor(private val repo: BBRepository) {

    suspend fun initPurchase(jsonObject: JsonObject):
            Flow<Resource<InitPurchaseResponse>> {
        return flow {
            emit(
                repo.initPurchaseRequest(jsonObject)
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun completePurchaseCart(jsonObject: JsonObject,isBalance:Boolean):
            Flow<Resource<PurchaseResponse>> {
        return flow {
            if (isBalance) {
                emit(
                    repo.purchaseOrder(jsonObject)
                )
            } else {
                emit(
                    repo.completePurchaseCart(jsonObject)

                )
            }

        }.flowOn(Dispatchers.IO)
    }

}
