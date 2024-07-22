package com.bitaqaty.reseller.ui.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
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
        mutableStateOf<DataState<ArrayList<Category>>?>(DataState.Loading)
    val categoryState: State<DataState<ArrayList<Category>>?> = _categoryState

//    init {
//        getCategoryList()
//    }
//
//    private fun getCategoryList() {
//        viewModelScope.launch {
//            repository.getCategoryList().collect { state ->
//                _categoryState.value = state
//            }
//        }
//    }

//    val categoryState: MutableState<DataState<ArrayList<Category>>> =
//        mutableStateOf(DataState.Loading)

    fun getCategoryList() {
        viewModelScope.launch {
            repo.getCategoryList()
                .catch {
        Log.e("dddd","sdfdsf")
                }
                .onEach {
                    _categoryState.value = it
                }.launchIn(viewModelScope)
        }
    }
}