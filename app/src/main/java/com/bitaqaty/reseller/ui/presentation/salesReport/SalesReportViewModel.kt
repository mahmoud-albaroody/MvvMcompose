package com.bitaqaty.reseller.ui.presentation.salesReport


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.data.model.ReportLog
import com.bitaqaty.reseller.domain.GetProductUseCase
import com.bitaqaty.reseller.domain.GetReportLogUseCase
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.utilities.Globals
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesReportViewModel @Inject constructor(private val getReportLogUseCase: GetReportLogUseCase) :
    ViewModel() {
    private val _getReport =
        MutableSharedFlow<ReportLog>()
    val getReport: MutableSharedFlow<ReportLog>
        get() = _getReport




    fun getSalesReportList(
        pageNumber: Int, categoryId: Int = -1, merchantId: Int, allowPaging: Boolean = true,
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("searchPeriod", "LAST_MONTH")
        jsonObject.addProperty("subAccountId", "311346")
        if (allowPaging) {
            jsonObject.addProperty("pageSize", Globals.PAGE_SIZE)
            jsonObject.addProperty("pageNumber", pageNumber)
        }
//        jsonObject.addProperty("customDateFrom", "")
//        jsonObject.addProperty("customDateTo", "")
//        jsonObject.addProperty("productId", "")
//
//        if (categoryId != -1) {
//            jsonObject.addProperty("categoryId", categoryId)
//        }
//        if (merchantId != -1) {
//            jsonObject.addProperty("merchantId", merchantId)
//        }
//        jsonObject.addProperty("channel", "")
//        jsonObject.addProperty("showRecommendedPrices", false)
//        jsonObject.addProperty("showSubResellerPrices", false)
//        jsonObject.addProperty("paymentMethod", "")

        viewModelScope.launch {
            getReportLogUseCase.generateHomeSalesReport(jsonObject)
                .catch {

                }
                .onEach {
                    _getReport.emit(it)
                }.launchIn(viewModelScope)
        }

    }



}


