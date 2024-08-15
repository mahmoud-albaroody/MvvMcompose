package com.bitaqaty.reseller.ui.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.ui.presentation.home.HomeViewModel
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    viewModel: HomeViewModel,
    userName: String? = null,
    balance: String? = null,
    merchants: ArrayList<Merchant>? = null
) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(BorderStroke(width = 0.1.dp, color = Color.Gray))
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center,
        ) {
            if(merchants.isNullOrEmpty()){
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null,
                        tint = Color.Blue
                    )
                    Column {
                        Text(
                            text = stringResource(id = R.string.hello) + userName,
                            fontSize = 12.sp,
                            fontFamily = frutigerLTArabic,
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontFamily = frutigerLTArabic,
                                )){
                                    append(stringResource(id = R.string.yourBalance))
                                }
                                withStyle(style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontFamily = frutigerLTArabic,
                                )){
                                    append("$balance ")
                                }
                                withStyle(style = SpanStyle(
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontFamily = frutigerLTArabic,
                                )){
                                    append(stringResource(id = R.string.sar))
                                }
                            }
                        )
                    }
                }
            }else{
                MerchantList(
                    viewModel = viewModel,
                    merchants = merchants,
                    scrollState = scrollState
                )
                if(merchants.size > 2){
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
                                    scrollState.animateScrollToItem(merchants.size - 1)
                                }
                            },
                        painter = painterResource(id = R.drawable.ic_forward_arrow),
                        contentDescription = "Forward",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun MerchantList(
    viewModel: HomeViewModel,
    merchants: List<Merchant>,
    scrollState: LazyListState,
) {
    var selectedMerchant by remember { mutableStateOf(if(merchants.isEmpty()) null else merchants.first()) }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = Dimens.padding8, horizontal = Dimens.padding4),
        state = scrollState
    ) {
        items(merchants) { merchant ->
            MerchantItem(
                merchant = merchant,
                isSelected = merchant == selectedMerchant,
                onClickMerchant = {
                    selectedMerchant = merchant

                    val productsInfo = ProductListRequest(
                        categoryId = viewModel._categoryId.value!!,
                        merchantId = merchant.id
                    )
                    viewModel.getProducts(productsInfo)
                }
            )
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TopBarPreview() {
//    TopBar()
//}