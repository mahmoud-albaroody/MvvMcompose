package com.bitaqaty.reseller.ui.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.ui.presentation.activity.MainActivityViewModel
import com.bitaqaty.reseller.ui.presentation.common.Empty
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.home.components.ChildMerchantList
import com.bitaqaty.reseller.ui.presentation.home.components.SideBar
import com.bitaqaty.reseller.ui.presentation.home.components.ProductList
import com.bitaqaty.reseller.ui.presentation.home.components.TopBar
import com.bitaqaty.reseller.ui.presentation.home.components.TopMerchantList
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.network.DataState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainActivityViewModel ,
    navController: NavController
) {
    val categoryState by viewModel.categoryState
    val topMerchantState by viewModel.topMerchantsState
    val merchantState by viewModel.merchantsState
    val productsState by viewModel.productsState
    val childMerchantsState by viewModel.childMerchantsState

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

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SideBar(
                            viewModel = viewModel,
                            mainViewModel = mainViewModel,
                            navController = navController,
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
                                TopMerchantList(
                                    viewModel = viewModel,
                                    topMerchants = topMerchants
                                )
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
                                        if(products.products.isEmpty()){
                                            Column() {
                                                Empty()
//                                                Text(
//                                                    modifier = Modifier.fillMaxWidth(),
//                                                    text = stringResource(R.string.no_products),
//                                                    textAlign = TextAlign.Center,
//                                                    fontSize = 16.sp,
//                                                    fontWeight = FontWeight.SemiBold,
//                                                    fontFamily = frutigerLTArabic,
//                                                    color = Color.Blue
//                                                )
                                            }
                                        }else{
                                            ProductList(products = products, isBottomSheetVisible) { product ->
                                                selectedProduct = product
                                                scope.launch {
                                                    isBottomSheetVisible = true
                                                    sheetState.expand()
                                                }
                                            }
                                        }
                                    }
                                    else -> {
                                        when(childMerchantsState) {
                                            is DataState.Loading -> {
                                                Loading()
                                            }
                                            is DataState.Error -> {
                                                val error =
                                                    (childMerchantsState as DataState.Error).exception
                                                Text(text = "Error: ${error.message}")
                                            }
                                            is DataState.Success -> {
                                                val childMerchants = (childMerchantsState as DataState.Success<TopChildMerchant>).data
                                                ChildMerchantList(
                                                    viewModel = viewModel,
                                                    childMerchants = childMerchants
                                                )
                                            }
                                            else -> {}
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
                            scope.launch {
                                isBottomSheetVisible = false
                                sheetState.hide()
                            }
                        })

                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomePreview() {
//    BitaqatyTheme {
//        HomeScreen()
//    }
//}