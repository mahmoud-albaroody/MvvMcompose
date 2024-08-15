package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.ReportRequestBody
import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class GetReportLogUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun generateHomeSalesReport(reportRequestBody: ReportRequestBody) =
        repo.generateHomeSalesReport(reportRequestBody)

    suspend fun getSimpleCategoryList() = repo.getSimpleCategoryList()

    suspend fun getSimpleMerchantList(categoryId: Int) =
        repo.getSimpleMerchantList(categoryId = categoryId)

    suspend fun getProductLookList(jsonObject: JsonObject) =
        repo.getProductLookList(jsonObject = jsonObject)

    suspend fun getSurePayRechargeMethods() =
        repo.getSurePayRechargeMethods()
    suspend fun getMerchants(categoryId:Int) =
        repo.getMerchants(categoryId)
    suspend fun getUserNamesList() =
        repo.getUserNamesList()

}