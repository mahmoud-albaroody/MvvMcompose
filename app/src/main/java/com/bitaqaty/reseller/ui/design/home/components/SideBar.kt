package com.bitaqaty.reseller.ui.design.home.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.ui.design.home.Category
import com.bitaqaty.reseller.ui.theme.merchantBg
import com.bitaqaty.reseller.utils.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(categories: List<Category>) {
    var selectedItem by remember { mutableStateOf<Category?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(74.dp)
            .background(color = Color(0xFFBFCCEC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedItem == category,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .animateItemPlacement()
                    .background(color = Color(0xFFBFCCEC))
                    .noRippleClickable { selectedItem = category }
            )
        }
    }
}

val sampleCategories = listOf(
    Category("Star", "Category 1"),
    Category("Star", "Category 2"),
    Category("Star", "Category 3"),
)

@Preview(showBackground = true)
@Composable
fun Preview() {
    CategoryList(categories = sampleCategories)
}