package com.bitaqaty.reseller.ui.design.productDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bitaqaty.reseller.ui.design.productDetails.components.BalancePayButton
import com.bitaqaty.reseller.ui.design.productDetails.components.ConfirmBalancePayButton
import com.bitaqaty.reseller.ui.design.productDetails.components.Counter
import com.bitaqaty.reseller.ui.design.productDetails.components.DoneButton
import com.bitaqaty.reseller.ui.design.productDetails.components.MadaPayButton
import com.bitaqaty.reseller.ui.design.productDetails.components.PrintVatButton
import com.bitaqaty.reseller.ui.design.productDetails.components.ProductInfo
import com.bitaqaty.reseller.ui.theme.arial
import com.bitaqaty.reseller.utils.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBottomSheet(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = RectangleShape,
            dragHandle = null,
            scrimColor = Color.Black.copy(alpha = .5f),
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            var isExpanded by remember { mutableStateOf(false) }
            val navigationBarHeight = with(LocalDensity.current) {
                WindowInsets.navigationBars.getBottom(this).toDp()
            }

            var isBalancePayClicked by remember { mutableStateOf(false) }
            var isConfirmBalancePayClicked by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .offset(y = (20).dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .noRippleClickable { onDismiss() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = Color(0xFF1C274C)
                )
            }

            val productDetailsList = mapOf(
                "Total Cost Price" to "72.08",
                "VAT 15%" to "2.00",
                "Total After VAT" to "77.00",
                "Suggested Selling Price" to "75.08",
                "Suggested Total Selling Price" to "2.00",
                "Suggested Total Selling Price After VAT" to "80.00",
            ).entries.toList()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isExpanded) {
                        Text(
                            modifier = Modifier.padding(top = 24.dp),
                            text = "Apple & iTunes Giftcard \$10 (US Store)\n",
                            fontFamily = arial,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                        )
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        ) {
                            items(items = productDetailsList) { productDetails ->
                                Row {
                                    Text(
                                        text = productDetails.key,
                                        fontFamily = arial,
                                        fontSize = 12.sp,
                                        color = Color(0xFF8D8D8D)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = productDetails.value,
                                        fontFamily = arial,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "SAR",
                                        fontFamily = arial,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF5F5F5F)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .border(width = 1.dp, color = Color.LightGray)
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(vertical = 28.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Counter()
                    Spacer(modifier = Modifier.width(32.dp))
                    ProductInfo( onClickInfo = { isExpanded = !isExpanded })
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if(!isBalancePayClicked){
                        BalancePayButton {isBalancePayClicked = true}
                        Spacer(modifier = Modifier.width(2.dp))
                        MadaPayButton {}
                    }else if(isBalancePayClicked && !isConfirmBalancePayClicked){
                        ConfirmBalancePayButton {isConfirmBalancePayClicked = true}
                        MadaPayButton {}
                    }else{
                        PrintVatButton {}
                        DoneButton {}
                    }
                }
            }
            Spacer(modifier = Modifier
                .height(navigationBarHeight))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = false, showSystemUi = true)
fun ProductDetailsBottomSheetPreview(){
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded
    )
    ProductDetailsBottomSheet(
        isBottomSheetVisible = true,
        sheetState = sheetState,
        onDismiss = {}
    )
}

