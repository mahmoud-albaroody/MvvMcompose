package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun getProductList(jsonObject: JsonObject) = repo.getProductList(jsonObject)

}