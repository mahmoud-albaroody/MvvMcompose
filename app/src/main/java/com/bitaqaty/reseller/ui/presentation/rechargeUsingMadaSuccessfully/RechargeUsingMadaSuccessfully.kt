package com.bitaqaty.reseller.ui.presentation.rechargeUsingMadaSuccessfully

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.navigation.Screen
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.Green
import com.bitaqaty.reseller.ui.theme.LightGrey200
import com.bitaqaty.reseller.ui.theme.LightGrey400

@Composable
fun RechargeUsingMadaScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: RechargeUsingMadaSuccessfullyViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {}
    RechargeLog(onHomePageClick = {
        navController.navigate(Screen.Home.route)
    })
}

@Composable
fun RechargeLog(onHomePageClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RechargeSuccessfullyPage(onHomePageClick = {
            onHomePageClick.invoke()
        })
    }
}


@Composable
fun RechargeSuccessfullyPage(onHomePageClick: () -> Unit) {

    Column(
        Modifier
            .fillMaxSize()

            .background(Color.White)
            .padding(top = Dimens.DefaultMargin10)
    ) {
        RechargeSuccessfully()
        NewBalance()
        HomePageButton(onHomePageClick = {
            onHomePageClick.invoke()
        })
    }
}


@Preview
@Composable
fun RechargeSuccessfully(
) {
    Card(
        Modifier
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            )
            .padding(top = Dimens.DefaultMargin)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        elevation = CardDefaults.cardElevation(Dimens.DefaultMargin1),
        colors = CardDefaults.cardColors(containerColor = Green)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.DefaultMargin),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(Dimens.halfDefaultMargin),
                painter = painterResource(R.drawable.ic_check),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(
                    bottom = Dimens.halfDefaultMargin,
                    top = Dimens.DefaultMargin
                ), text = stringResource(R.string.recharged_successfully),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )


        }

    }
}


@Composable
fun NewBalance(
) {
    Card(
        Modifier
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            )
            .padding(top = Dimens.DefaultMargin)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.DefaultMargin10),
        border = BorderStroke(Dimens.DefaultMargin0, LightGrey400),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.DefaultMargin),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(
                    bottom = Dimens.halfDefaultMargin,
                    top = Dimens.DefaultMargin
                ), text = "Your New Balance",
                style = TextStyle(
                    color = LightGrey200,
                    fontSize = 16.sp
                )
            )
            Text(
                modifier = Modifier.padding(
                    bottom = Dimens.halfDefaultMargin,
                    top = Dimens.halfDefaultMargin
                ), text = "15000,00",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )


        }

    }
}


@Composable
fun HomePageButton(onHomePageClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.DefaultMargin,
                vertical = Dimens.halfDefaultMargin
            )
            .padding(top = Dimens.DefaultMargin),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = {
            onHomePageClick.invoke()
        },
        border = BorderStroke(Dimens.DefaultMargin1, BebeBlue)
    )
    {
        Text(
            text = "Home Page",
            modifier = Modifier.padding(vertical = Dimens.DefaultMargin),
            color = BebeBlue,
            fontSize = 16.sp
        )
    }

}


