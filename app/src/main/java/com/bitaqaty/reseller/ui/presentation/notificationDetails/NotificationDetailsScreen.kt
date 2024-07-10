package com.bitaqaty.reseller.ui.presentation.notificationDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.ui.presentation.notifications.NotificationHeader
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200

@Composable
fun NotificationDetailsScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: NotificationDetailsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
    }
    NotificationDetails()
}

@Preview
@Composable
fun NotificationDetails() {
    Card(
        Modifier
            .fillMaxSize()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        colors = CardDefaults.cardColors(containerColor = LightGrey100)
    ) {
        Column(modifier = Modifier.padding(Dimens.DefaultMargin)) {
            NotificationHeader()
            Text(
                modifier = Modifier.padding(top = Dimens.DefaultMargin20),
                text = "GDD has developed the most all-encompassing distribution network in the region; enabling MENA’s internet users to access thousands of products worldwide. GDD has developed the most all-encompassing distribution network in the region; enabling MENA’s internet users to access thousands of products worldwide. - GDD has developed the most all-encompassing distribution - GDD has developed th ompassing distribution",
                style = TextStyle(color = LightGrey200)
            )
        }

    }
}




