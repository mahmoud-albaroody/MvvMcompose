package com.bitaqaty.reseller.ui.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.home.components.SideBar
import com.bitaqaty.reseller.ui.presentation.home.components.ProductList
import com.bitaqaty.reseller.ui.presentation.home.components.TopBar
import com.bitaqaty.reseller.ui.presentation.home.components.TopMerchantList
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.utilities.network.DataState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categoryState by viewModel.categoryState
    val topMerchantState by viewModel.topMerchantsState
    val merchantState by viewModel.merchantsState
    val productsState by viewModel.productsState

    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })

    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(key1 = true) {
        viewModel.getCategoryList()
    }

    when(categoryState){
        is DataState.Loading -> {
            Loading()
        }
        is DataState.Error -> {
            val error = (categoryState as DataState.Error).exception
            Text(text = "Error: ${error.message}")
        }
        is DataState.Success -> {
            val categoryList = (categoryState as DataState.Success<ArrayList<Category>>).data
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    when(merchantState){
                        is DataState.Error -> {
                            val error = (categoryState as DataState.Error).exception
                            Text(text = "Error: ${error.message}")
                        }
                        is DataState.Success -> {
                            val merchants = (merchantState as DataState.Success<ArrayList<Merchant>>).data
                            TopBar(viewModel = viewModel, merchants = merchants)
                        }
                        else -> {
                            TopBar(viewModel = viewModel)
                        }
                    }

                    Row {
                        SideBar(
                            viewModel = viewModel,
                            categories = categoryList
                        )
                        when(topMerchantState){
                            is DataState.Loading -> {
                                Loading()
                            }
                            is DataState.Error -> {
                                val error = (topMerchantState as DataState.Error).exception
                                Text(text = "Error: ${error.message}")
                            }
                            is DataState.Success -> {
                                val topMerchants = (topMerchantState as DataState.Success<TopMerchants>).data
                                TopMerchantList(topMerchants = topMerchants)
                            }
                            else -> {
                                when(productsState){
                                    is DataState.Loading -> {
                                        Loading()
                                    }
                                    is DataState.Error -> {
                                        val error = (topMerchantState as DataState.Error).exception
                                        Text(text = "Error: ${error.message}")
                                    }
                                    is DataState.Success -> {
                                        val products = (productsState as DataState.Success<ProductListResponse>).data
                                        ProductList(products = products, isBottomSheetVisible) { product ->
                                            selectedProduct = product
                                            scope.launch {
                                                isBottomSheetVisible = true
                                                sheetState.expand()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    ProductDetailsBottomSheet(
                        product = selectedProduct,
                        isBottomSheetVisible = isBottomSheetVisible,
                        sheetState = sheetState,
                        onDismiss = {
                            scope.launch { sheetState.hide() }
                                .invokeOnCompletion { isBottomSheetVisible = false }
                        })
                }
            }
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