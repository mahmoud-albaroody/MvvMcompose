package com.bitaqaty.reseller.ui.presentation.accountManager

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.applyFilter.FilterButton
import com.bitaqaty.reseller.ui.theme.Blue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils

@Composable
fun AccountManagerScreen(
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
        AccountManageDetails(
            modifier,
            name,
            mobile
        )
    }

}


@Composable
fun AccountManageDetails(modifier: Modifier, name: String?, mobile: String?) {
    val context = LocalContext.current
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        name?.let {
            Text(
                modifier = modifier.padding(
                    top = Dimens.padding24,
                    bottom = Dimens.padding12
                ),
                text = it,
                fontSize = Dimens.fontSize12,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            modifier = modifier.padding(bottom = Dimens.padding12),
            fontSize = Dimens.fontSize12,
            text = stringResource(id = R.string.contact_number)
        )
        mobile?.let {
            FilterButton(
                Blue,
                it,
                true,
                White,
                false,
                R.drawable.ic_call,
                Dimens.padding50,
                onApplyFilterClick = {
                    try {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data =
                            Uri.parse(
                                "tel:" +
                                        mobile
                            )
                        context.startActivity(callIntent)
                    } catch (e: Exception) {

                    }
                })
        }
    }
}