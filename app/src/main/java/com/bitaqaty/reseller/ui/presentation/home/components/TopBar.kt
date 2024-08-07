package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.home.Merchant
import com.bitaqaty.reseller.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun TopBar() {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Image(
            modifier = Modifier
                .size(Dimens.bitaqatyLogo)
                .background(Color.White)
                .border(0.1.dp, Color.Gray),
            painter = painterResource(id = R.drawable.bitaqaty_logo),
            contentDescription = "Logo",
        )
        val sampleMerchants = listOf(
            Merchant("USA", "USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USE","USA",  "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USC","USA",  "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("UST","USA",  "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USL", "USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USI","USA",  "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
            Merchant("USO", "USA", "https://img-cdn.pixlr.com/image-generator/history/65bb506dcb310754719cf81f/ede935de-1138-4f66-8ed7-44bd16efc709/medium.webp"),
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center,
        ) {
            MerchantList(merchants = sampleMerchants, scrollState = scrollState)
            Icon(
                modifier = Modifier
                    .height(48.dp)
                    .width(38.dp)
                    .align(Alignment.CenterEnd)
                    .background(
                        Color.LightGray.copy(alpha = 0.4f),
                        shape = MaterialTheme.shapes.extraSmall
                    )
                    .clickable {
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(sampleMerchants.size - 1)
                        }
                    },
                painter = painterResource(id = R.drawable.ic_forward_arrow),
                contentDescription = "Forward",
                tint = Color.White,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarPreview() {
    TopBar()
}