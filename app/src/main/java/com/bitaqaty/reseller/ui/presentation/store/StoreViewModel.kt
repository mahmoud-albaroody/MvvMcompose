package com.bitaqaty.reseller.ui.presentation.store

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.utilities.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repo: BBRepository
) : ViewModel() {
    private val _categoryState =
        mutableStateOf<DataState<ArrayList<Category>>>(DataState.Loading)
    val categoryState: State<DataState<ArrayList<Category>>> = _categoryState

    private val _editState =
        mutableStateOf<DataState<Unit>?>(null)
    val editState: State<DataState<Unit>?> = _editState

    fun getCategoryList() {
        viewModelScope.launch {
            repo.getCategoryList().collect { categoryState ->
                _categoryState.value = categoryState
            }
        }
    }

    fun editCategory(currentCategoryId: Int, newCategoryId: Int){
        viewModelScope.launch{
            repo.editCategory(currentCategoryId, newCategoryId).collect{ state ->
                _editState.value = state
            }
        }
    }
}