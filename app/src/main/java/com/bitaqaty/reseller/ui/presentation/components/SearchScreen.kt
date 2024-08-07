package com.bitaqaty.reseller.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.ui.presentation.home.components.MerchantList
import com.bitaqaty.reseller.ui.presentation.home.components.Product
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label
import kotlinx.coroutines.launch

@Composable
fun SearchScreen() {
    Column {
        SearchBar()
        LazyColumn {
            item {
                Text(
                    modifier = Modifier.padding(Dimens.padding12),
                    text = stringResource(R.string.category_label),
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            item {
                SearchCategoryList(items = 6)
            }
            item {
                Text(
                    modifier = Modifier.padding(Dimens.padding12),
                    text = stringResource(R.string.merchant_label),
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            val sampleMerchants = listOf(
                Merchant("USA","USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USE","USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USC", "USA","https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("UST", "USA","https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USL", "USA","https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USI","USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USO","USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            )
            item {
                MerchantList(sampleMerchants, LazyListState(), Color.Transparent)
            }
            item {
                Text(
                    modifier = Modifier.padding(Dimens.padding12),
                    text = "Select Card",
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            item {
                SearchProductList(12)
            }
        }
    }
}

@Composable
fun SearchCategoryList(items: Int) {
    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(items) { _ ->
            SearchCategory()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductList(items: Int) {
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )

    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(items) { _ ->
            Product(onClick = {
                scope.launch {
                    isBottomSheetVisible = true
                    sheetState.expand()
                }
            })
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
fun SearchScreenPreview() {
    BitaqatyTheme {
        SearchScreen()
    }
}