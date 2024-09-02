package com.bitaqaty.reseller.ui.presentation.favoriteScreen


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.presentation.common.Empty
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.common.ProductItem
import com.bitaqaty.reseller.ui.presentation.productDetails.ProductDetailsBottomSheet
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.utilities.network.DataState
import kotlinx.coroutines.launch

@Composable
fun FavoraiteScreen(navController: NavController, modifier: Modifier) {
    val favoriteViewModel: FavoraiteViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    Favoraite(
        navController = navController,
        favoriteViewModel = favoriteViewModel,
//        onItemClick = {
//            navController.navigate(Screen.SelectMainCategoryScreen.route)
//        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favoraite(
    navController: NavController,
    favoriteViewModel: FavoraiteViewModel,
    //onItemClick: () -> Unit
) {
    val favoriteListState by favoriteViewModel.favoriteListState
    val editState by favoriteViewModel.editFavoriteState

    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden })

    LaunchedEffect(key1 = editState) {
        favoriteViewModel.getFavoriteProducts()
    }

    when (favoriteListState) {
        is DataState.Loading -> Loading()
        is DataState.Error -> {}
        is DataState.Success -> {
            val favoriteProducts = (favoriteListState as DataState.Success).data.products
            if(favoriteProducts.isNotEmpty()){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.halfDefaultMargin),
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(Dimens.fourDefaultMargin),
                ) {
                    items(favoriteProducts) { product ->
                        ProductItem(
                            product = product,
                            isSelected = product == selectedProduct && isBottomSheetVisible
                        ) {
                            selectedProduct = product
                            scope.launch {
                                isBottomSheetVisible = true
                                sheetState.expand()
                            }
                        }
                    }
                }
            }else{
                Empty()
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

}





