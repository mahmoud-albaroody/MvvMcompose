package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class RechargeUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun validateSurePayCharging(jsonObject: JsonObject) =
        repo.validateSurePayCharging(jsonObject)

    suspend fun partnerRecharge(jsonObject: JsonObject) =
        repo.surePayCharging(jsonObject)
}
