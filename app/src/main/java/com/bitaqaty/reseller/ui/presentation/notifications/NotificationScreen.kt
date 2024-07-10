package com.bitaqaty.reseller.ui.presentation.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.LightGrey200

@Composable
fun NotificationScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
    }

    Notification()

}

@Preview
@Composable
fun Notification() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.White), content = {
            items(4) {
                NotificationItem()
            }
        })
}

@Preview
@Composable
fun NotificationItem() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        elevation = CardDefaults.cardElevation(Dimens.DefaultMargin1),
//        border = BorderStroke(
//            1.dp, LightGrey200
//        ),
        colors = CardDefaults.cardColors(containerColor = LightGrey100)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.DefaultMargin,
                        vertical = Dimens.fourDefaultMargin
                    )
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {

                NotificationHeader()

                Text(
                    modifier = Modifier.padding(
                        bottom = Dimens.halfDefaultMargin,
                        top = Dimens.DefaultMargin
                    ), text = "Check details",
                    style = TextStyle(color = BebeBlue, fontSize = 11.sp)
                )
            }
            Image(
                modifier = Modifier.padding(Dimens.halfDefaultMargin),
                painter = painterResource(R.drawable.ic_forward_arrow),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun NotificationHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = "12/12/2023. 03:11:55",
        style = TextStyle(color = LightGrey200, fontSize = 12.sp)
    )

    Text(
        text = "New system update",
        style = TextStyle(color = FontColor, fontSize = 14.sp)
    )
}



