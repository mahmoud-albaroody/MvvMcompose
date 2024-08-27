package com.bitaqaty.reseller.ui.presentation.productDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductInfo
import com.bitaqaty.reseller.data.model.PurchaseRequest
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel() {
    private val _purchaseState =
        mutableStateOf<DataState<PurchaseResponse>>(DataState.Loading)
    val purchaseState: State<DataState<PurchaseResponse>> = _purchaseState

    private var _counter = mutableIntStateOf(1)
    val counter: State<Int> = _counter

    private val _alpha = mutableFloatStateOf(1f)
    val alpha: State<Float> = _alpha

    fun purchaseOrder(product: Product){
        val price = product.getPrice()
        val productOrder = arrayListOf(
            ProductInfo(
                productId = product.getProductsId(),
                currentPrice = if(price.isNotEmpty() && price != "0.0" && price != "0") price else "",
                itemsCount = counter.value
            )
        )
        val purchaseRequest = PurchaseRequest(isCart = false, productInfo = productOrder)
        viewModelScope.launch {
            repo.purchaseOrder(purchaseRequest).collect { state ->
                _purchaseState.value = state
            }
        }
    }

    fun onDecrease(){
        if (_counter.intValue > 1) _counter.intValue--
    }

    fun onIncrease(){
        _counter.intValue++
    }

    fun toggleOpacity(alpha: Float){
        _alpha.floatValue = alpha
    }
}