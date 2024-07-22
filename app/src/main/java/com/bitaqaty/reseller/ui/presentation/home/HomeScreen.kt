package com.bitaqaty.reseller.ui.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.home.components.CategoryList
import com.bitaqaty.reseller.ui.presentation.home.components.Product
import com.bitaqaty.reseller.ui.presentation.home.components.TopBar
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.utils.network.DataState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categoryState by viewModel.categoryState

    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )
    when(categoryState){
        is DataState.Loading -> {
            CircularProgressIndicator(color = Color.Black)
        }
        is DataState.Error -> {
            val error = (categoryState as DataState.Error).exception
            Text(text = "Error: ${error.message}")
        }
        is DataState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TopBar()
//                    val sampleCategories = listOf(
//                        Category("Category 1", "Category 1"),
//                        Category("Category 2", "Category 2"),
//                        Category("Category 3", "Category 3"),
//                    )
                    val categoryList = (categoryState as DataState.Success<ArrayList<Category>>).data
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
                                Product(
                                    onClick = {
                                        scope.launch {
                                            isBottomSheetVisible = true
                                            sheetState.expand()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    ProductDetailsBottomSheet(
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        }
                    )
                }
            }
        }
    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            TopBar()
//            val sampleCategories = listOf(
//                Category("Category 1", "Category 1"),
//                Category("Category 2", "Category 2"),
//                Category("Category 3", "Category 3"),
//            )
//            Row {
//                CategoryList(categories = sampleCategories)
//                LazyVerticalGrid(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = Dimens.halfDefaultMargin),
//                    columns = GridCells.Fixed(2),
//                    contentPadding = PaddingValues(Dimens.fourDefaultMargin),
//                ) {
//
//                    items(10) { _ ->
//                        Product(
//                            onClick = {
//                                scope.launch {
//                                    isBottomSheetVisible = true
//                                    sheetState.expand()
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
//            ProductDetailsBottomSheet(
//                isBottomSheetVisible = isBottomSheetVisible,
//                sheetState = sheetState,
//                onDismiss = {
//                    scope.launch { sheetState.hide() }
//                        .invokeOnCompletion { isBottomSheetVisible = false }
//                }
//            )
//        }
//    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomePreview() {
//    BitaqatyTheme {
//        HomeScreen()
//    }
//}