package com.bitaqaty.reseller.ui.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.ChildMerchantRequest
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel() {
    private val _categoryState =
        mutableStateOf<DataState<ArrayList<Category>>>(DataState.Loading)
    val categoryState: State<DataState<ArrayList<Category>>> = _categoryState

    private val _topMerchantsState =
        mutableStateOf<DataState<TopMerchants>?>(null)
    val topMerchantsState: State<DataState<TopMerchants>?> = _topMerchantsState

    private val _childMerchantsState =
        mutableStateOf<DataState<TopChildMerchant>?>(null)
    val childMerchantsState: State<DataState<TopChildMerchant>?> = _childMerchantsState

    private val _merchantsState =
        mutableStateOf<DataState<ArrayList<Merchant>>?>(null)
    val merchantsState: State<DataState<ArrayList<Merchant>>?> = _merchantsState

    private val _productsState =
        mutableStateOf<DataState<ProductListResponse>?>(null)
    val productsState: State<DataState<ProductListResponse>?> = _productsState

    val _categoryId = mutableStateOf<Int?>(null)

    val isCategory = mutableStateOf(false)

    fun getCategoryList() {
        viewModelScope.launch {
            val featuredCategory = Category(nameEn = "Featured")
            repo.getCategoryList().collect { categoryState ->
                _categoryState.value = categoryState
                if(categoryState is DataState.Success){
                    getTopMerchants()
                    categoryState.data.add(0, featuredCategory)
                }
            }
        }
    }

    fun getTopMerchants(){
        viewModelScope.launch {
            repo.getTopMerchants().collect{ state ->
                _topMerchantsState.value = state
                _merchantsState.value = null
            }
        }
    }

    fun getChildMerchants(categoryId: Int){
        val childMerchantRequest = ChildMerchantRequest(categoryId = categoryId)
        viewModelScope.launch {
            repo.getChildMerchants(childMerchantRequest).collect{ state ->
                _childMerchantsState.value = state
                _topMerchantsState.value = null
                _productsState.value = null
            }
        }
    }

    fun getMerchants(categoryId: Int){
        viewModelScope.launch {
            repo.getMerchants(categoryId).collect{ state ->
                _merchantsState.value = state
                if(state is DataState.Success){
                    val productsInfo = ProductListRequest(
                        categoryId = categoryId,
                        merchantId = state.data.first().id
                    )
                    getProducts(productsInfo)
                    _topMerchantsState.value = null
                    _categoryId.value = categoryId
                }
            }
        }
    }

    fun getProducts(productsInfo: ProductListRequest){
        viewModelScope.launch {
            repo.getProducts(productsInfo).collect{state ->
                _productsState.value = state
            }
        }
        _childMerchantsState.value = null
        _topMerchantsState.value = null
    }
}