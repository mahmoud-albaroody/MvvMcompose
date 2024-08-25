package com.bitaqaty.reseller.ui.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.home.components.CategoryList
import com.bitaqaty.reseller.ui.presentation.home.components.Product
import com.bitaqaty.reseller.ui.presentation.home.components.TopBar
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.Utils.saveUserData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categoryStatee = viewModel.categoryState
    val getProfileState = viewModel.getProfile
    val getSystemSettings = viewModel.getSystemSetting
    val scope = rememberCoroutineScope()
    val categoryList = remember { mutableStateListOf<Category>() }
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })
    LaunchedEffect(key1 = true) {
        viewModel.getProfile()
        viewModel.getCategoryList()

        viewModel.viewModelScope.launch {
            getSystemSettings.collect {
                Globals.SETTINGS = it
            }
        }
        viewModel.viewModelScope.launch {
            getProfileState.collect {
                saveUserData(it)
                viewModel.getSystemSetting()
            }
        }
    }
    categoryStatee.value.let {
        when (it) {
//            is DataState.Loading -> {
//                Log.e("dddd", "sfsdfdsf")
//            }
//
//            is DataState.Success<ArrayList<Category>> -> {
//                categoryList.clear()
//                categoryList.addAll(it.data)
//
//            }

            else -> {

            }
        }
    }




    Box(modifier = Modifier.fillMaxSize().background(White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()

            Row {
                CategoryList(categories = categoryList)

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.halfDefaultMargin),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Dimens.fourDefaultMargin),
                ) {
                    items(10) { _ ->
                        Product(onClick = {
                            scope.launch {
                                isBottomSheetVisible = true
                                sheetState.expand()
                            }
                        })
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            ProductDetailsBottomSheet(isBottomSheetVisible = isBottomSheetVisible,
                sheetState = sheetState,
                onDismiss = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { isBottomSheetVisible = false }
                })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        HomeScreen()
    }
}