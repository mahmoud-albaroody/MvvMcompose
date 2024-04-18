package com.bitaqaty.reseller.ui.bottom_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "HomeScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "SearchScreen", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center))
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
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    BitaqatyTheme {
        HomeScreen()
    }
}