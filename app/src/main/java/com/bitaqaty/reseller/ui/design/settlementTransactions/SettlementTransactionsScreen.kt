package com.bitaqaty.reseller.ui.design.settlementTransactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.PlaceHolder
import com.bitaqaty.reseller.ui.theme.SearchBarBackground
import com.bitaqaty.reseller.ui.theme.SearchBarText
import com.bitaqaty.reseller.utils.AppConstant.MIN_Transfer_AMOUNT

@Composable
fun SettlementTransactionsScreen(){
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp),
    ){
        item { SettlementHeader() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { BasicData() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { BankStatements() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { AdditionalNotes() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { SendButton() }
    }
}

@Composable
fun SettlementHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Available Balance",
                color = Color.Gray
            )
            Text(
                text = "838.88 SAR",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.ic_wallet),
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
fun BasicData(){
    Column {
        Text(
            text = "Basic Data",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Transfer amount*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "The requested transfer amount",
            validationType = ValidationType.TRANSFER_AMOUNT,
            keyBoardType = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "CR number*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "CR number"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Company Name*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "Company Name"
        )
    }
}

@Composable
fun BankStatements(){
    Column {
        Text(
            text = "Bank Statements",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Swift Code*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "Swift Code"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Bank Name*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "Bank Name"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "IBAN*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            placeHolder = "IBAN",
            validationType = ValidationType.IBAN
        )
    }
}

@Composable
fun AdditionalNotes(){
    Column {
        Text(
            text = "Additional Notes",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Additional Notes"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            modifier = Modifier
                .height(120.dp),
            placeHolder = "Additional Notes",
            singleLine = false,
            validationType = ValidationType.NOTES
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                modifier = Modifier
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null,
                tint = Color.Blue
            )
            Text(
                text = "Minimum amount to request 100.0 SAR",
                color = Color.Blue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SendButton(){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue, shape = RoundedCornerShape(10.dp)),
        onClick = {},
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text(
            text = "Send",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    singleLine: Boolean = true,
    validationType: ValidationType = ValidationType.DEFAULT,
    keyBoardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(SearchBarBackground),
        value = text,
        onValueChange = {
            text = it
            isError = validationType != ValidationType.NOTES && it.isEmpty()
                    || (validationType == ValidationType.TRANSFER_AMOUNT && !validateAmount(it).isValid)
                    || (validationType == ValidationType.IBAN && !validateIBAN(it).isValid)
            errorMessage = when {
                it.isEmpty() -> "*This field is required"
                validationType == ValidationType.TRANSFER_AMOUNT && !validateAmount(it).isValid ->
                    validateAmount(it).errorMessage
                validationType == ValidationType.IBAN && !validateIBAN(it).isValid ->
                    validateIBAN(it).errorMessage
                else -> ""
            }
        },
        isError = isError,
        textStyle = MaterialTheme.typography.PlaceHolder,
        placeholder = {
            Text(
                modifier = modifier,
                text = placeHolder,
                style = MaterialTheme.typography.PlaceHolder,
                color = SearchBarText,
                maxLines = 1,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SearchBarBackground,
            unfocusedContainerColor = SearchBarBackground,
            focusedTextColor = Color.Blue,
            cursorColor = Color.Gray,
            focusedBorderColor = Color.Blue,
            errorContainerColor = Color.Transparent,
        ),
        singleLine = singleLine,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = keyBoardType
    )
    if(isError){
        Text(
            text = errorMessage,
            color = Color.Red
        )
    }
}

private fun validateAmount(amount: String): Validation{
    return if(amount.toFloat() < MIN_Transfer_AMOUNT){
        Validation(
            isValid = false,
            errorMessage = "*The Minimum amount to request is $MIN_Transfer_AMOUNT"
        )
    }else{
        Validation(isValid = true)
    }
}

private fun validateIBAN(IBAN: String): Validation{
    return when {
        IBAN.length >= 2 && IBAN.substring(0,2) != "SA" -> {
            Validation(false, "*IBAN code should started with SA")
        }
        IBAN.trim().length != 24 -> {
            Validation(false, "*Invalid IBAN code")
        }
        IBAN.length == 24 && IBAN.trim().substring(2,24).any { !it.isDigit() } -> {
            Validation(false, "*22 number must be entered")
        }
        else -> {
            Validation(true)
        }
    }
}
data class Validation(
    val isValid: Boolean,
    val errorMessage: String = ""
)
enum class ValidationType {
    DEFAULT,
    TRANSFER_AMOUNT,
    IBAN,
    NOTES
}
@Composable
@Preview(showSystemUi = true)
fun SettlementTransactionsScreenPreview(){
    SettlementTransactionsScreen()
}