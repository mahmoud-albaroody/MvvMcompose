package com.bitaqaty.reseller.ui.design.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.ui.design.home.components.CategoryList
import com.bitaqaty.reseller.ui.design.home.components.Product
import com.bitaqaty.reseller.ui.design.home.components.TopBar
import com.bitaqaty.reseller.ui.design.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        val sampleCategories = listOf(
            Category("Category 1", "Category 1"),
            Category("Category 2", "Category 2"),
            Category("Category 3", "Category 3"),
        )
        Row {
            CategoryList(categories = sampleCategories)

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
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
                item {
                    Spacer(modifier = Modifier.height(116.dp))
                }
            }
        }
    }
    ProductDetailsBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismiss = {
            scope.launch { sheetState.hide() }
                .invokeOnCompletion { isBottomSheetVisible = false }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        HomeScreen()
    }
}