package com.bitaqaty.reseller.ui.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.repository.SubSellerRepository
import com.bitaqaty.reseller.data.repository.SubSellerRepositoryInterface
import com.bitaqaty.reseller.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SubSellerRepository
) : ViewModel() {
    private val _categoryState = mutableStateOf<DataState<ArrayList<Category>>>(DataState.Loading)
    val categoryState: State<DataState<ArrayList<Category>>> = _categoryState

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            repository.getCategoryList().collect { state ->
                _categoryState.value = state
            }
        }
    }
}