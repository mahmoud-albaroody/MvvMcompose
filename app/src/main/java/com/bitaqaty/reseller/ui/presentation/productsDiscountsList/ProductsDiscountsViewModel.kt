package com.bitaqaty.reseller.ui.presentation.productsDiscountsList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.data.model.ProductListResult
import com.bitaqaty.reseller.domain.AuthenticationUseCase
import com.bitaqaty.reseller.domain.GetProductUseCase
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
class ProductsDiscountsViewModel @Inject constructor(private val getProductList: GetProductUseCase) :
    ViewModel() {
    private val _getProduct =
        MutableSharedFlow<ProductListResult>()
    val getProduct: MutableSharedFlow<ProductListResult>
        get() = _getProduct

    fun getProductDiscountList(
        pageNumber: Int, categoryId: Int = -1, merchantId: Int,
        searchText: String = "", allowPaging: Boolean = true,
        orderByProducts: Boolean = true
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("applyPagination", allowPaging)
        jsonObject.addProperty("resellerId", CurrentUser.getInstance()?.reseller?.id)
        if (allowPaging) {
            jsonObject.addProperty("pageSize", Globals.PAGE_SIZE)
            jsonObject.addProperty("pageNumber", pageNumber)
        }
        jsonObject.addProperty("calculateVat", true)
        jsonObject.addProperty("orderByProducts", orderByProducts)
        jsonObject.addProperty("byUrl", false)

        if (categoryId != -1) {
            jsonObject.addProperty("categoryId", categoryId)
        }
        if (merchantId != -1) {
            jsonObject.addProperty("merchantId", merchantId)
        }
        if (searchText.isNotEmpty()) {
            jsonObject.addProperty("searchCriteria", searchText)
        }
        viewModelScope.launch {
            getProductList.getProductList(jsonObject)
                .catch {

                }
                .onEach {
                    _getProduct.emit(it)
                }.launchIn(viewModelScope)
        }
    }
}
