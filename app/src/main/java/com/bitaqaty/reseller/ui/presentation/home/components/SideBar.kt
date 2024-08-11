package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.utilities.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SideBar(
    viewModel: HomeViewModel,
    categories: List<Category>,
) {
    var selectedItem by remember { mutableStateOf<Category?>(categories.first()) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(74.dp)
            .background(color = Color(0xFFBFCCEC)),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        viewModel.getMerchants(category.id)
                    }else{
                        viewModel.getTopMerchants()
                    }
                    selectedItem = category
                },
                onLongPress = {showDialog = true}
            )
        }
    }
    if(showDialog){
        ConfirmationDialog(
            onConfirm = {},
            onDismiss = {showDialog = false}
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Preview() {
//    CategoryList(categories = sampleCategories)
//}