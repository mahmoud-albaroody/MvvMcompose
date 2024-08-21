package com.bitaqaty.reseller.ui.presentation.productDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor() : ViewModel() {
    private var _counter = mutableIntStateOf(1)
    val counter: State<Int> = _counter

    fun onDecrease(){
        if (_counter.intValue > 1) _counter.intValue--
    }

    fun onIncrease(){
        _counter.intValue++
    }
}