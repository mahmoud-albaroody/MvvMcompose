package com.bitaqaty.reseller.ui.presentation.selectSubCategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.presentation.selectMainCategory.SelectMainCategoryItems


@Composable
fun SelectSubCategoryScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: SelectSubCategoryViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    SelectMainCategory(onItemClick = {
        
    })
}


@Composable
fun SelectMainCategory(onItemClick: () -> Unit) {
    SelectMainCategoryItems(onItemClick = {

    })
}










