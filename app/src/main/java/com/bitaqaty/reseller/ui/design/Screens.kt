package com.bitaqaty.reseller.ui.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.home.Category
import com.bitaqaty.reseller.ui.design.home.Merchant
import com.bitaqaty.reseller.ui.design.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.design.home.components.Product
import com.bitaqaty.reseller.ui.design.home.components.CategoryList
import com.bitaqaty.reseller.ui.design.home.components.MerchantList
import com.bitaqaty.reseller.ui.design.home.components.TopBar
import com.bitaqaty.reseller.ui.design.home.components.sampleCategories
import com.bitaqaty.reseller.ui.design.search.components.SearchBar
import com.bitaqaty.reseller.ui.design.search.components.SearchCategory
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label
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
        TopBar(onClick = {
            scope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }
        })
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
                items(10) { index ->
                    Product()
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

@Composable
fun SearchScreen(modifier: Modifier = Modifier.padding(start = Dimens.padding12)) {
    Column {
        SearchBar()
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.category_label),
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            item {
                Spacer(modifier = Modifier.height(Dimens.padding6))
            }
            item {
                SearchCategoryList(items = 6)
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.merchant_label),
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            val sampleMerchants = listOf(
                Merchant("USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USE", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USC", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("UST", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USL", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USI", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
                Merchant("USO", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            )
            item {
                MerchantList(sampleMerchants, LazyListState(), Color.Transparent)
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    modifier = modifier,
                    text = "Select Card",
                    style = MaterialTheme.typography.Label,
                    fontSize = 16.sp,
                    color = label
                )
            }
            item {
                SearchProductList(12)
            }
            item{
                Spacer(modifier = Modifier.height(140.dp))
            }
        }
    }
}

@Composable
fun SearchCategoryList(items: Int) {
    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(items) { item ->
            SearchCategory()
        }
    }
}

@Composable
fun SearchProductList(items: Int) {
    LazyRow(
        contentPadding = PaddingValues(start = Dimens.padding12)
    ) {
        items(items) { item ->
            Product()
        }
    }
}

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "FavoriteScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun StoreScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "StoreScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun TransactionsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "TransactionsScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun MoreScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "MoreScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}

// Pop up to the start destination of the graph to
// avoid building up a large stack of destinations
// on the back stack as users select items
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        HomeScreen()
    }
}