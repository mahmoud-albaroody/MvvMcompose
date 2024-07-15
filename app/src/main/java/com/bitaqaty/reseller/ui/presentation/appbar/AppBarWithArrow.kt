package com.bitaqaty.reseller.ui.presentation.appbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun AppBarWithArrow(
    title: String?, haveBack: Boolean,
    pressOnBack: () -> Unit
) {

    TopAppBar(
        elevation = 6.dp,
        backgroundColor = Color.White,
        modifier = Modifier.height(58.dp)
    ) {
        Row {
            if (haveBack)
                Card(
                    modifier = Modifier
                        .clickable {
                            pressOnBack.invoke()
                        }
                        .padding(start = Dimens.halfDefaultMargin),
                    shape = RoundedCornerShape(Dimens.DefaultMargin10),
                    border = BorderStroke(0.25.dp, BebeBlue),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back_24),
                        colorFilter = ColorFilter.tint(LightGrey400),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(Dimens.halfDefaultMargin)

                    )
                }
        }

        Spacer(modifier = Modifier.width(Dimens.halfDefaultMargin))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically),
            text = title ?: "",
            style = MaterialTheme.typography.h6,
            color = LightGrey400,
            textAlign = TextAlign.Center
        )
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

        Spacer(modifier = Modifier.width(Dimens.halfDefaultMargin))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically),
            text = "title" ?: "",
            style = MaterialTheme.typography.h6,
            color = LightGrey400,
            textAlign = TextAlign.Center
        )
    }

}
