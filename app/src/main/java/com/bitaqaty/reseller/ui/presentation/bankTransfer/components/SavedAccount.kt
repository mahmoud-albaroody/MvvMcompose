package com.bitaqaty.reseller.ui.presentation.bankTransfer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.presentation.bankTransfer.BankTransferViewModel
import com.bitaqaty.reseller.utilities.noRippleClickable

@Composable
fun SavedAccount(
    modifier: Modifier = Modifier,
    bankName: String,
    countryName: String,
    senderName: String,
    bankAccount: String,
    isSelected: Boolean,
    onDeleteClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(250.dp)
            .padding(10.dp)
            .noRippleClickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF1F5F8)
        )
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = bankName,
                        modifier = Modifier.padding(end = 4.dp),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )
                    Text(
                        text = "($countryName)",
                        color = Color.Black
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                Icon(
                    imageVector = if(isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = if(isSelected) Color.Blue else Color.LightGray,
                    modifier = Modifier
                        .size(16.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sender),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = senderName,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bank_account),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = bankAccount,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Bottom)
                        .clickable(onClick = onDeleteClick),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SavedAccountPreview(){
//    SavedAccount(
//        bankName = "Bank Name",
//        countryName = "Egypt",
//        senderName = "Mahmoud",
//        bankAccount = "123554448",
//        isSelected = true
//    ) {
//
//    }
//}