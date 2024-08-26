package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.activity.MainActivityViewModel
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.utilities.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SideBar(
    homeViewModel: HomeViewModel,
    mainViewModel: MainActivityViewModel,
    navController: NavController,
    categories: List<Category>,
) {
    var selectedItem by remember { mutableStateOf<Category?>(categories.first()) }
    var categoryToChangeId by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var categoryName by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(74.dp)
            .background(color = Color(0xFFBFCCEC)),
        horizontalAlignment = Alignment.Start
    ) {
        items(categories.subList(0, 5)) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedItem == category,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .animateItemPlacement()
                    .background(color = Color(0xFFBFCCEC))
                    .noRippleClickable { selectedItem = category },
                onClickCategory = { index ->
                    if(index != 0){
                        homeViewModel.getMerchants(category.id)
                    }else{
                        homeViewModel.getTopMerchants()
                    }
                    selectedItem = category
                },
                onLongPress = { index ->
                    categoryName = category.getName()
                    if(index != 0){
                        categoryToChangeId = category.id
                        showDialog = true
                    }
                }
            )
            if(showDialog){
                ConfirmationDialog(
                    categoryName = categoryName,
                    onConfirm = {
                        showDialog = false
                        navController.navigate("store/$categoryToChangeId")
                        mainViewModel.showBottomNav.value = false
                    },
                    onDismiss = {showDialog = false}
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    CategoryList(categories = sampleCategories)
//}