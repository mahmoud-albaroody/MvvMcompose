package com.bitaqaty.reseller.ui.presentation.appbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bitaqaty.reseller.ui.theme.BebeBlue


@Composable
fun HomeAppBar(title: String, openDrawer: () -> Unit, openFilters: () -> Unit) {
    TopAppBar(
        backgroundColor = BebeBlue,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6, color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(Icons.Default.Menu, "Menu",
                    tint = Color.White)
            }
        },
        /*actions = {
            IconButton(onClick = openFilters) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }*/
    )
}
