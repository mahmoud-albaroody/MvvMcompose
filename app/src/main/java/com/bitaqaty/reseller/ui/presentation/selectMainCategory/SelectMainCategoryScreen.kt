package com.bitaqaty.reseller.ui.presentation.selectMainCategory


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun SelectMainCategoryScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: SelectMainCategoryViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    SelectMainCategoryItems(onItemClick = {
        navController.navigate(Screen.SelectSubCategoryScreen.route)
    })
}


@Composable
fun SelectMainCategoryItems(onItemClick: () -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.White), content = {
            items(10) {
                SelectMainCategoryItem(onItemClick = {
                    onItemClick.invoke()
                })
            }
        })
}

@Composable
fun SelectMainCategoryItem(onItemClick: () -> Unit) {
    Card(
        Modifier
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.defaultMargin6
            )
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            },
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, LightGrey400),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                    vertical = Dimens.DefaultMargin20
                )
                .fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = FontColor,
                fontSize = 14.sp,
            ),
            text = "iTunes Giftcards"
        )
    }
}








