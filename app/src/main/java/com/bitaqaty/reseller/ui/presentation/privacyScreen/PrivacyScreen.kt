package com.bitaqaty.reseller.ui.presentation.privacyScreen

import android.os.Build
import android.text.Html
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Privay
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey100
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.noRippleClickable

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PrivacyScreen(
    navController: NavController,
    modifier: Modifier,
    comFrom: String

) {
    Box(
        Modifier
            .background(White)
            .fillMaxSize()
    ) {
        Privacy(comFrom)
    }

}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Privacy(comFrom: String) {
    val privacyLis = remember { mutableListOf<Privay>() }
    var headerArr = listOf<String>()
    var bodyArr = listOf<String>()
    if (comFrom == "1") {
        headerArr =
            stringArrayResource(R.array.more_terms_of_use_header_arr).toList()
        bodyArr =
            stringArrayResource(R.array.more_terms_of_use_body_arr).toList()
    } else if (comFrom == "2") {
        headerArr =
            stringArrayResource(R.array.more_faq_header_arr).toList()
        bodyArr =
            stringArrayResource(R.array.more_faq_body_arr).toList()
    } else if (comFrom == "3") {
        headerArr =
            stringArrayResource(R.array.more_privacy_policy_header_arr).toList()
        bodyArr =
            stringArrayResource(R.array.more_privacy_policy_body_arr).toList()
    }

//    privacyLis.clear()
    for (i in bodyArr.toList().indices) {
        privacyLis.add(Privay(headerArr[i], bodyArr[i], false))

    }

    Column(
        Modifier
            .fillMaxWidth()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(content = {
            item {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.halfDefaultMargin,
                            horizontal = Dimens.halfDefaultMargin
                        ),
                    shape = RoundedCornerShape(0),
                    colors = CardDefaults.cardColors(containerColor = LightGrey100)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = Dimens.halfDefaultMargin,
                                horizontal = Dimens.halfDefaultMargin
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = Dimens.padding14),
                            text = stringResource(id = R.string.more_terms_of_use),
                            fontSize = Dimens.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        Image(

                            painter = painterResource(R.drawable.ic_terms),
                            contentDescription = ""
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = Dimens.halfDefaultMargin,
                            horizontal = Dimens.halfDefaultMargin
                        ),
                    fontSize = Dimens.fontSize12,
                    text = stringResource(id = R.string.more_terms_of_use_overview)
                )

            }
            items(privacyLis) {
                PrivacyItem(it)
            }
        })

    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PrivacyItem(privacy: Privay) {
    var isVisible by remember { mutableStateOf(false) }
    isVisible
    Card(
        Modifier
            .fillMaxWidth()
            .background(White)
            .noRippleClickable {
                privacy.isVisible = !privacy.isVisible
                isVisible = !isVisible


            }
            .padding(
                vertical = Dimens.fourDefaultMargin,
                horizontal = Dimens.halfDefaultMargin
            ),
        shape = RoundedCornerShape(Dimens.padding12),
        colors = CardDefaults.cardColors(containerColor = LightGrey100)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.padding14),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.padding20, bottom = Dimens.padding12),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_back_24),
                    contentDescription = ""
                )
                Text(
                    text = privacy.header,
                    fontSize = Dimens.fontSize12,
                    modifier = Modifier.padding(horizontal = Dimens.padding12)
                )
            }
            if (privacy.isVisible)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.padding20),
                    fontSize = Dimens.fontSize10,
                    text = Html.fromHtml(privacy.body, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
                )
        }
    }
}
