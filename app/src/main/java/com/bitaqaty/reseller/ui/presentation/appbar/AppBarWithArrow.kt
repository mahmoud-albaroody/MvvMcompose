package com.bitaqaty.reseller.ui.presentation.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.CurrentUser
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun AppBarWithArrow(
    title: String?,
    haveSubTitle: Boolean = false,
    haveBack: Boolean,
    pressOnBack: () -> Unit
) {

    TopAppBar(
        elevation = 6.dp,
        backgroundColor = Color.White,
        modifier = Modifier.height(58.dp)
    ) {
        Box {


//            Spacer(modifier = Modifier.width(Dimens.halfDefaultMargin))

            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = title ?: "",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                if(haveSubTitle){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically),
                            text = stringResource(id = R.string.TLogPricesCurrency).replace(
                                "[X]", CurrentUser.getInstance()?.reseller?.getCurrentCurrency() ?: "SAR"
                            ),
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (haveBack)
                    Card(
                        modifier = Modifier

                            .padding(start = Dimens.halfDefaultMargin),
                        shape = RoundedCornerShape(Dimens.DefaultMargin10),
                        border = BorderStroke(0.25.dp, BebeBlue),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_back_24),
                            colorFilter = ColorFilter.tint(LightGrey400),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    pressOnBack.invoke()
                                }
                                .padding(Dimens.halfDefaultMargin)

                        )
                    }
            }
        }
    }

}

@Preview
@Composable
fun AppBarWithArrow(

) {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = Color.White,
        modifier = Modifier.height(58.dp)
    ) {
        Box {


//            Spacer(modifier = Modifier.width(Dimens.halfDefaultMargin))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = "title" ?: "",
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Row {
                if (true)
                    Card(
                        modifier = Modifier
                            .padding(start = Dimens.halfDefaultMargin),
                        shape = RoundedCornerShape(Dimens.fourDefaultMargin),
                        border = BorderStroke(0.25.dp, BebeBlue),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_back_24),
                            colorFilter = ColorFilter.tint(LightGrey400),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(Dimens.halfDefaultMargin)
                                .clickable {

                                }
                        )
                    }
            }
        }
    }

}
