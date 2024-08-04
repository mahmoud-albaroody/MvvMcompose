package com.bitaqaty.reseller.ui.presentation.settlementRequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.theme.PlaceHolder
import com.bitaqaty.reseller.ui.theme.SearchBarBackground
import com.bitaqaty.reseller.ui.theme.SearchBarText
import com.bitaqaty.reseller.utilities.network.DataState

@Composable
fun SettlementRequestScreen(
    viewModel: SettlementRequestViewModel = hiltViewModel()
){
    val systemSettingsState by viewModel.systemSettingsState
    val bankDataState by viewModel.personalBankData
    val minAmount = viewModel.minTransferAmount

    LaunchedEffect(key1 =  true){
        viewModel.getSystemSettings()
        viewModel.getPersonalBankData()
    }

    if(systemSettingsState is DataState.Loading || bankDataState is DataState.Loading){
        Loading()
    }else if(systemSettingsState is DataState.Error || bankDataState is DataState.Error){
        val error = (bankDataState as DataState.Error).exception
        Text(text = "Error: ${error.message}")
    }else{
        val bankData = (bankDataState as DataState.Success<PersonalBankData>).data
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ){
            item { SettlementHeader() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                BasicData(
                    crNum = bankData.crNumber ?: "",
                    companyName = bankData.companyName ?: ""
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                BankStatements(
                    swiftCode = bankData.swiftCode ?: "",
                    bankName = bankData.bankName ?: "",
                    IBAN = bankData.iban ?: ""
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                AdditionalNotes(
                    notes = bankData.notes ?: "",
                    minAmount = minAmount
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SendButton() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
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
fun BasicData(
    crNum: String = "",
    companyName: String = ""
){
    Column {
        Text(
            text = "Basic Data",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Transfer amount*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            placeHolder = "The requested transfer amount",
            validationType = ValidationType.TRANSFER_AMOUNT,
            keyBoardType = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "CR number*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            initialVal = crNum,
            placeHolder = "CR number"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Company Name*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            initialVal = companyName,
            placeHolder = "Company Name"
        )
    }
}

@Composable
fun BankStatements(
    swiftCode: String = "",
    bankName: String = "",
    IBAN: String = ""
){
    Column {
        Text(
            text = "Bank Statements",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Swift Code*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            initialVal = swiftCode,
            placeHolder = "Swift Code"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Bank Name*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            initialVal = bankName,
            placeHolder = "Bank Name"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "IBAN*"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            initialVal = IBAN,
            placeHolder = "IBAN",
            validationType = ValidationType.IBAN
        )
    }
}

@Composable
fun AdditionalNotes(
    notes: String = "",
    minAmount: String
){
    Column {
        Text(
            text = "Additional Notes",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Additional Notes"
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            modifier = Modifier
                .height(120.dp),
            initialVal = notes,
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
                text = "Minimum amount to request $minAmount SAR",
                color = Color.Blue,
                fontSize = 12.sp,
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
fun ValidationTextField(
    modifier: Modifier = Modifier,
    viewModel: SettlementRequestViewModel = hiltViewModel(),
    initialVal: String = "",
    placeHolder: String,
    singleLine: Boolean = true,
    validationType: ValidationType = ValidationType.DEFAULT,
    keyBoardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
) {
    val updatedText by viewModel.text
    var textState by remember { mutableStateOf(TextFieldValue(initialVal.ifEmpty { updatedText })) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(SearchBarBackground),
        value = textState,
        onValueChange = {
            viewModel.validateStartWithZeroOrDecimalPoint(it.text)
            textState = it.copy(text = updatedText, selection = TextRange(updatedText.length))
            isError = viewModel.validateSettlementRequest(it.text, validationType).isError
            errorMessage = viewModel.validateSettlementRequest(it.text, validationType).errorMessage
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
        keyboardOptions = keyBoardType,
    )
    if(isError){
        Text(
            text = errorMessage,
            color = Color.Red
        )
    }
}
//@Composable
//@Preview(showSystemUi = true)
//fun SettlementTransactionsScreenPreview(){
//    SettlementRequestScreen()
//}