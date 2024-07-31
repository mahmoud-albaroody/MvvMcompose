package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class GetReportLogUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun generateHomeSalesReport(jsonObject: JsonObject) = repo.generateHomeSalesReport(jsonObject)

}