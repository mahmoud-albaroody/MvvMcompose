package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

//import com.bitaqaty.reseller.data.repository.MovieRepository

class TransactionLogUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun invoke(jsonObject: JsonObject) = repo.getTransactionLogList(jsonObject)
}