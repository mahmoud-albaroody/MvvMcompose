package com.bitaqaty.reseller.ui.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.home.components.MerchantList
import com.bitaqaty.reseller.ui.presentation.home.components.ProductItem
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val categoryState by viewModel.categoryState
    val merchantState by viewModel.merchantsState
    val productsState by viewModel.productsState

    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })


    LaunchedEffect(key1 = true) {
        viewModel.getCategoryList()
    }

    Column {
        SearchBar()
        when(categoryState){
            is DataState.Loading -> Loading()
            is DataState.Error -> {
                val error = (categoryState as DataState.Error).exception
                Text(text = "Error: ${error.message}")
            }
            else -> {
                val categories = (categoryState as DataState.Success<ArrayList<Category>>).data

                LazyColumn {
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = stringResource(R.string.category_label),
                            style = MaterialTheme.typography.Label,
                            fontSize = 16.sp,
                            color = label
                        )
                    }
                    item {
                        SearchCategoryList(
                            viewModel = viewModel,
                            categories = categories
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = stringResource(R.string.merchant_label),
                            style = MaterialTheme.typography.Label,
                            fontSize = 16.sp,
                            color = label
                        )
                    }
                    item {
                        when(merchantState){
                            is DataState.Loading -> Loading()
                            is DataState.Error -> {
                                val error = (merchantState as DataState.Error).exception
                                Text(text = "Error: ${error.message}")
                            }
                            is DataState.Success -> {
                                val merchants = (merchantState as DataState.Success<ArrayList<Merchant>>).data
                                SearchMerchantList(
                                    viewModel = viewModel,
                                    merchants = merchants,
                                )
                            }
                        }
                    }
                    item {
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
                                    text = "Select Card",
                                    style = MaterialTheme.typography.Label,
                                    fontSize = 16.sp,
                                    color = label
                                )

                                SearchProductList(
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
}

@Composable
fun SearchCategoryList(
    viewModel: SearchViewModel,
    categories: ArrayList<Category>
) {
    var selectedItem by remember { mutableStateOf<Category?>(categories.first()) }

    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(categories) { category ->
            SearchCategory(
                category = category,
                isSelected = selectedItem == category,
                onClickCategory = { inedx ->
                    viewModel.getMerchants(category.id)
                    selectedItem = category
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductList(
    products: ProductListResponse,
    isSheetStateVisible: Boolean,
    onClickProduct: (Product) -> Unit
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(products.products) { product ->
            ProductItem(
                product = product,
                isSelected = product == selectedProduct && isSheetStateVisible
            ){
                selectedProduct = product
                onClickProduct(product)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    BitaqatyTheme {
        SearchScreen()
    }
}