package com.bitaqaty.reseller.ui.design.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun SearchCategoryList(categories: List<CategoryItem>) {
    var selectedItem by remember { mutableStateOf<CategoryItem?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(96.dp)
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categories) { category ->
            Category(
                contentDescription = category.contentDescription,
                name = category.name,
                isSelected = selectedItem == category,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(color = Color.LightGray)
                    .clickable { selectedItem = category }
            )
        }
    }
}

data class CategoryItem(
    val contentDescription: String,
    val name: String,
)

val sampleCategories = listOf(
    CategoryItem("Star", "Category 1"),
    CategoryItem("Star", "Category 2"),
    CategoryItem("Star", "Category 3"),
)

@Preview(showBackground = true)
@Composable
fun Preview() {
    SearchCategoryList(categories = sampleCategories)
}