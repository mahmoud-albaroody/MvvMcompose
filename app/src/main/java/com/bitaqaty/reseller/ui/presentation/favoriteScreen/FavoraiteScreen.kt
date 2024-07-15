package com.bitaqaty.reseller.ui.presentation.favoriteScreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400

@Composable
fun FavoraiteScreen(navController: NavController, modifier: Modifier) {
    val resetPasswordViewModel: FavoraiteViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    Favoraite(onItemClick = {
        navController.navigate(Screen.SelectMainCategoryScreen.route)
    })
}

@Composable
fun Favoraite(onItemClick: () -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        content = {
            items(10) {
                FavoraiteItem(onItemClick = {
                    onItemClick.invoke()
                })
            }
        })
}


@Composable
fun FavoraiteItem(onItemClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable {
            onItemClick.invoke()
        }
        .padding(
            horizontal = Dimens.halfDefaultMargin,
            vertical = Dimens.halfDefaultMargin
        ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = DefaultBackgroundColor)) {
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_add_fav),
                colorFilter = ColorFilter.tint(LightGrey400),
                contentDescription = null,
                modifier = Modifier.padding(

                    vertical = Dimens.padding45
                )

            )
        }
    }
}



