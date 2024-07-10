package com.bitaqaty.reseller.ui.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.home.components.CategoryItem
import com.bitaqaty.reseller.ui.design.home.components.Product
import com.bitaqaty.reseller.ui.design.home.components.SearchCategoryList
import com.bitaqaty.reseller.ui.design.home.components.TopBar
import com.bitaqaty.reseller.ui.design.search.components.SearchBar
import com.bitaqaty.reseller.ui.design.search.components.SearchCategory
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        val sampleCategories = listOf(
            CategoryItem("Star", "Category 1"),
            CategoryItem("Star", "Category 2"),
            CategoryItem("Star", "Category 3"),
        )
        SearchCategoryList(categories = sampleCategories)
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier.padding(start = Dimens.padding12)) {
    Column {
        SearchBar()
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(Dimens.padding12))
            }
            item {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.category_label),
                    style = MaterialTheme.typography.Label,
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
                Spacer(modifier = Modifier.height(Dimens.padding12))
            }
            item {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.merchant_label),
                    style = MaterialTheme.typography.Label,
                    color = label
                )
            }
            item {
                SearchProductList(items = 6)
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
        SearchScreen()
    }
}