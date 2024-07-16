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
import com.bitaqaty.reseller.ui.design.home.HomeScreen
import com.bitaqaty.reseller.ui.design.home.Merchant
import com.bitaqaty.reseller.ui.design.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.design.home.components.Product
import com.bitaqaty.reseller.ui.design.home.components.CategoryList
import com.bitaqaty.reseller.ui.design.home.components.MerchantList
import com.bitaqaty.reseller.ui.design.home.components.TopBar
import com.bitaqaty.reseller.ui.design.home.components.sampleCategories
import com.bitaqaty.reseller.ui.design.search.components.SearchBar
import com.bitaqaty.reseller.ui.design.search.components.SearchCategory
import com.bitaqaty.reseller.ui.design.search.components.SearchScreen
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Label
import com.bitaqaty.reseller.ui.theme.label
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    HomeScreen()
}

@Composable
fun Search() {
    SearchScreen()
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