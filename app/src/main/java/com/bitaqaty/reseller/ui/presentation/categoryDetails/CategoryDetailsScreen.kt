package com.bitaqaty.reseller.ui.presentation.categoryDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label
import com.bitaqaty.reseller.utilities.network.DataState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CategoryDetailsViewModel = hiltViewModel(),
    categoryId: String
){
    val merchantState by viewModel.merchantsState
    val productsState by viewModel.productsState

    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })

    LaunchedEffect(key1 = true) {
        viewModel.getMerchants(categoryId.toInt())
    }

    Column {
        when(merchantState){
            is DataState.Loading -> Loading()
            is DataState.Error -> {
                val error = (merchantState as DataState.Error).exception
                Text(text = "Error: ${error.message}")
            }
            else -> {
                val merchants = (merchantState as DataState.Success<ArrayList<Merchant>>).data
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = stringResource(R.string.merchant_label),
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
                CategoryMerchantList(viewModel = viewModel, merchants = merchants)
                when(productsState){
                    is DataState.Loading -> Loading()
                    is DataState.Error -> {
                        val error = (productsState as DataState.Error).exception
                        Text(text = "Error: ${error.message}")
                    }
                    is DataState.Success -> {
                        val products = (productsState as DataState.Success<ProductListResponse>).data
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = stringResource(id = R.string.select_card),
                            style = MaterialTheme.typography.Label,
                            fontSize = 16.sp,
                            color = label
                        )
                        CategoryProductList(
                            products = products,
                            isBottomSheetVisible
                        ){ product ->
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
        ProductDetailsBottomSheet(
            navController = navController,
            product = selectedProduct,
            isBottomSheetVisible = isBottomSheetVisible,
            sheetState = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { isBottomSheetVisible = false }
            }
        )
    }
}

