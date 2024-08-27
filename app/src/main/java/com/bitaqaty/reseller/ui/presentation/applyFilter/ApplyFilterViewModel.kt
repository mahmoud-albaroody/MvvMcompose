package com.bitaqaty.reseller.ui.presentation.applyFilter


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.LogUserName
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.domain.GetReportLogUseCase
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyFilterViewModel @Inject constructor(private val getReportLogUseCase: GetReportLogUseCase) :
    ViewModel() {
    private val _getSimpleCategoryList = MutableSharedFlow<ArrayList<Category>>()
    val getSimpleCategoryList: MutableSharedFlow<ArrayList<Category>>
        get() = _getSimpleCategoryList


    private val _getSimpleMerchantList = MutableSharedFlow<ArrayList<Merchant>>()
    val getSimpleMerchantList: MutableSharedFlow<ArrayList<Merchant>>
        get() = _getSimpleMerchantList


    private val _getProductLookList = MutableSharedFlow<ArrayList<Product>>()
    val getProductLookList: MutableSharedFlow<ArrayList<Product>>
        get() = _getProductLookList

    private val _getSurePayRechargeMethods = MutableSharedFlow<ArrayList<RechargeMethod>>()
    val getSurePayRechargeMethods: MutableSharedFlow<ArrayList<RechargeMethod>>
        get() = _getSurePayRechargeMethods

    private val _getUserNamesList = MutableSharedFlow<ArrayList<LogUserName>>()
    val getUserNamesList: MutableSharedFlow<ArrayList<LogUserName>>
        get() = _getUserNamesList


    fun getSimpleCategoryList() {
        viewModelScope.launch {
            getReportLogUseCase.getSimpleCategoryList().catch {

                }.onEach {
                    _getSimpleCategoryList.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun getSimpleMerchantList(categoryId: Int) {

        viewModelScope.launch {
            getReportLogUseCase.getMerchants(categoryId).collect { state ->
                if (state is DataState.Success) {
                    _getSimpleMerchantList.emit(state.data)
                }
            }
        }
    }

    fun getSurePayRechargeMethods() {
        viewModelScope.launch {
            getReportLogUseCase.getSurePayRechargeMethods().catch {

                }.onEach {
                    _getSurePayRechargeMethods.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun getMerchants(categoryId: Int) {
        viewModelScope.launch {
            getReportLogUseCase.getMerchants(categoryId).collect { state ->
                if (state is DataState.Success) {
                    _getSimpleMerchantList.emit(state.data)
                }
            }
        }
    }


    fun getUserNamesList() {
        viewModelScope.launch {
            getReportLogUseCase.getUserNamesList().catch {

                }.onEach {
                    _getUserNamesList.emit(it)
                }.launchIn(viewModelScope)
        }
    }

    fun getProductLookList(categoryId: Int, merchantId: Int) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("resellerId", Utils.getUserData()?.reseller?.id)
        jsonObject.addProperty("categoryId", categoryId)
        jsonObject.addProperty("merchantId", merchantId)
        jsonObject.addProperty("applyPagination", false)
        viewModelScope.launch {
            getReportLogUseCase.getProductLookList(jsonObject).catch {

                }.onEach {
                    _getProductLookList.emit(it)
                }.launchIn(viewModelScope)
        }
    }


}