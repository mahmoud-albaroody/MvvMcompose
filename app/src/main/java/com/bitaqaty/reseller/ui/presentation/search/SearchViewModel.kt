package com.bitaqaty.reseller.ui.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel() {
    private val _categoryState =
        mutableStateOf<DataState<ArrayList<Category>>>(DataState.Loading)
    val categoryState: State<DataState<ArrayList<Category>>> = _categoryState

    private val _merchantsState =
        mutableStateOf<DataState<ArrayList<Merchant>>>(DataState.Loading)
    val merchantsState: State<DataState<ArrayList<Merchant>>> = _merchantsState

    private val _productsState =
        mutableStateOf<DataState<ProductListResponse>>(DataState.Loading)
    val productsState: State<DataState<ProductListResponse>> = _productsState

    val _categoryId = mutableStateOf<Int?>(null)

    fun getCategoryList() {
        viewModelScope.launch {
            repo.getCategoryList().collect { categoryState ->
                _categoryState.value = categoryState
                if(categoryState is DataState.Success){
                    getMerchants(categoryState.data.first().id)
                }
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
    }
}