package com.bitaqaty.reseller.ui.design.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.design.home.Merchant
import com.bitaqaty.reseller.ui.design.theme.dimens

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            //.padding(top = 30.dp)
            .fillMaxWidth()
            .height(64.dp)
            .wrapContentHeight(),
    ) {
        Image(
            modifier = Modifier
                .size(MaterialTheme.dimens.bitaqatyLogo)
                .background(Color.White)
                .border(0.1.dp, Color.Gray),
            painter = painterResource(id = R.drawable.bitaqaty_logo),
            contentDescription = "Logo",
        )
        val sampleMerchants = listOf(
            Merchant("USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USE", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USC", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("UST", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USL", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USI", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USO", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center,
        ) {
            MerchantList(merchants = sampleMerchants)
            Icon(
                modifier = Modifier
                    .height(64.dp)
                    .width(38.dp)
                    .align(Alignment.CenterEnd)
                    .background(
                        Color.LightGray.copy(alpha = 0.4f),
                        shape = MaterialTheme.shapes.extraSmall
                    ),
                painter = painterResource(id = R.drawable.ic_forward_arrow),
                contentDescription = "Forward",
                tint = Color.White,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarPreview() {
    TopBar()
}