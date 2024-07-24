package com.bitaqaty.reseller.ui.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        mutableStateOf<DataState<TopMerchants>>(DataState.Loading)
    val topMerchantsState: State<DataState<TopMerchants>> = _topMerchantsState

    private val _merchantsState =
        mutableStateOf<DataState<ArrayList<Merchant>>?>(null)
    val merchantsState: State<DataState<ArrayList<Merchant>>?> = _merchantsState

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

    private fun getTopMerchants(){
        viewModelScope.launch {
            repo.getTopMerchants().collect{ state ->
                _topMerchantsState.value = state
            }
        }
    }

    fun getMerchants(categoryId: Int){
        viewModelScope.launch {
            repo.getMerchants(categoryId).collect{ state ->
                _merchantsState.value = state
            }
        }
    }
}