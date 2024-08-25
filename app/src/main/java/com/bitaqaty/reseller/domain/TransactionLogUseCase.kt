package com.bitaqaty.reseller.domain

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


class TransactionLogUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun transactionLog(transactionRequestBody: TransactionRequestBody):
            Flow<Resource<TransactionLogResult>> {
        return flow {
            emit(
                repo.getTransactionLogList(transactionRequestBody)
            )
        }.flowOn(Dispatchers.IO)
    }
}