package com.bitaqaty.reseller.ui.presentation.termsAndConditions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils

@Composable
fun TermsAndConditionsScreen(
    navController: NavController,
    modifier: Modifier,

    ) {
    val name = Utils.getUserData()?.accountManagerDTO?.representativeName
    val mobile = Utils.getUserData()?.accountManagerDTO?.representativeMobile
    Box(
        Modifier
            .background(White)
            .fillMaxSize()
    ) {
        TermsAndConditions(modifier)
    }

}


@Composable
fun TermsAndConditions(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding24)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(
                top = Dimens.padding24,
                bottom = Dimens.padding12
            ),
            text = stringResource(id = R.string.amex_terms),
            fontSize = Dimens.fontSize10,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = modifier.padding(bottom = Dimens.padding12),
            fontSize = Dimens.fontSize10,
            text = stringResource(id = R.string.amex_content)
        )
        Text(
            modifier = modifier.padding(bottom = Dimens.padding12),
            fontSize = Dimens.fontSize10,
            text = stringResource(id = R.string.amex_content)
        )
    }
}