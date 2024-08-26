package com.bitaqaty.reseller.ui.presentation.settlementRequest

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.theme.SearchBarBackground
import com.bitaqaty.reseller.ui.theme.SearchBarText
import com.bitaqaty.reseller.utilities.network.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun SettlementRequestScreen(
    navController: NavController,
    viewModel: SettlementRequestViewModel = hiltViewModel()
){
    val systemSettingsState by viewModel.systemSettingsState
    val bankDataState by viewModel.personalBankData
    val settlementRequestState by viewModel.settlementRequestState
    val userState by viewModel.user

    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    val focusRequesters = remember {
        List(6) { FocusRequester() }
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 =  true){
        viewModel.getSystemSettings()
        viewModel.getPersonalBankData()
        viewModel.getUser()
    }

    when {
        systemSettingsState is DataState.Loading
                || bankDataState is DataState.Loading
                || userState is DataState.Loading-> {
            Loading()
        }
        systemSettingsState is DataState.Error || bankDataState is DataState.Error -> {
            val error = (bankDataState as DataState.Error).exception
            Text(text = "Error: ${error.message}")
        }
        else -> {
            if(settlementRequestState is DataState.Loading){
                Loading()
            }
            if(settlementRequestState is DataState.Success){
                val request = (settlementRequestState as DataState.Success<SettlementRequestResult>).data
                Toast.makeText(context, request.errors.first().errorMessage, Toast.LENGTH_LONG).show()
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (settlementRequestState is DataState.Loading) 0.5f else 1f)
                    .padding(horizontal = 12.dp)
            ){
                item { Spacer(modifier = Modifier.height(12.dp)) }
                item { SettlementHeader(viewModel = viewModel) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { BasicData(
                    viewModel = viewModel,
                    focusRequesters = focusRequesters.subList(0, 3)
                ) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { BankStatements(
                    viewModel = viewModel,
                    focusRequesters = focusRequesters.subList(3, 6)
                ) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { AdditionalNotes(
                    viewModel = viewModel,
                    focusManager = focusManager
                ) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { SendButton(
                    viewModel = viewModel,
                    focusRequesters = focusRequesters,
                    lazyListState = lazyListState,
                    scope = scope
                ) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun SettlementHeader(
    viewModel: SettlementRequestViewModel
){
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
                text = stringResource(id = R.string.available_balance),
                color = Color.Gray
            )
            Text(
                text = viewModel.balance,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.ic_coin),
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
fun BasicData(
    viewModel: SettlementRequestViewModel,
    focusRequesters: List<FocusRequester>
){
    Column {
        Text(
            text = stringResource(id = R.string.basic_data),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.btrr_transfer_amount_required)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[0],
            value = viewModel.settlementRequestUiState.collectAsState().value.amount,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(amount = value)} },
            placeHolder = stringResource(id = R.string.the_requested_transfer_amount),
            validationType = ValidationType.TRANSFER_AMOUNT,
            keyBoardType = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.cr_number)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[1],
            value = viewModel.settlementRequestUiState.collectAsState().value.crNum,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(crNum = value)} },
            placeHolder = stringResource(id = R.string.cr_number_hint),
            validationType = ValidationType.CR_NUM
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.company_name)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[2],
            value = viewModel.settlementRequestUiState.collectAsState().value.companyName,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(companyName = value)} },
            placeHolder = stringResource(id = R.string.company_name_hint),
            validationType = ValidationType.COMPANY_NAME
        )
    }
}

@Composable
fun BankStatements(
    viewModel: SettlementRequestViewModel,
    focusRequesters: List<FocusRequester>
){
    Column {
        Text(
            text = stringResource(id = R.string.bank_statements),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.swift_code)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[0],
            value = viewModel.settlementRequestUiState.collectAsState().value.swiftCode,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(swiftCode = value)} },
            placeHolder = stringResource(id = R.string.swift_code_hint),
            validationType = ValidationType.SWIFT_CODE
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.bank_name)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[1],
            value = viewModel.settlementRequestUiState.collectAsState().value.bankName,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(bankName = value)} },
            placeHolder = stringResource(id = R.string.bank_name_hint),
            validationType = ValidationType.BANK_NAME
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.iban)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            focusRequester = focusRequesters[2],
            value = viewModel.settlementRequestUiState.collectAsState().value.IBAN,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(IBAN = value)} },
            placeHolder = stringResource(id = R.string.btrr_iban),
            validationType = ValidationType.IBAN
        )
    }
}

@Composable
fun AdditionalNotes(
    viewModel: SettlementRequestViewModel,
    focusManager: FocusManager
){
    Column {
        Text(
            text = stringResource(id = R.string.additional_notes),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.additional_notes)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ValidationTextField(
            viewModel = viewModel,
            value = viewModel.settlementRequestUiState.collectAsState().value.notes,
            onValueChange = { value -> viewModel.settlementRequestUiState.update {it.copy(notes = value)} },
            modifier = Modifier
                .height(120.dp),
            placeHolder = stringResource(id = R.string.additional_notes),
            singleLine = false,
            validationType = ValidationType.NOTES,
            keyBoardType = KeyboardOptions(imeAction = ImeAction.Done),
            focusManager = focusManager
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(12.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_info_parentheses),
                contentDescription = null,
                tint = Color.Blue
            )
            Text(
                text = stringResource(id = R.string.minimum_amount_to_request) + viewModel.minTransferAmount + " " + stringResource(id = R.string.sar),
                color = Color.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SendButton(
    viewModel: SettlementRequestViewModel,
    focusRequesters: List<FocusRequester>,
    lazyListState: LazyListState,
    scope: CoroutineScope
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Blue, shape = RoundedCornerShape(10.dp)),
        onClick = {
            val settlementRequestError = viewModel.settlementRequestUiState.value.errors
            if(settlementRequestError == Errors()){
                viewModel.sendSettlementRequest()
            }else{
                scope.launch {
                    when{
                        settlementRequestError.amountError -> {
                            lazyListState.animateScrollToItem(0)
                            focusRequesters[0].requestFocus()
                            keyboardController?.show()
                        }
                        settlementRequestError.crNumError -> {
                            lazyListState.animateScrollToItem(1)
                            focusRequesters[1].requestFocus()
                            keyboardController?.show()
                        }
                        settlementRequestError.companyNameError -> {
                            lazyListState.animateScrollToItem(2)
                            focusRequesters[2].requestFocus()
                            keyboardController?.show()
                        }
                        settlementRequestError.swiftCodeError -> {
                            lazyListState.animateScrollToItem(3)
                            focusRequesters[3].requestFocus()
                            keyboardController?.show()
                        }
                        settlementRequestError.bankNameError -> {
                            lazyListState.animateScrollToItem(4)
                            focusRequesters[4].requestFocus()
                            keyboardController?.show()
                        }
                        settlementRequestError.IBANError -> {
                            lazyListState.animateScrollToItem(5)
                            focusRequesters[5].requestFocus()
                            keyboardController?.show()
                        }
                    }
                }
            }
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text(
            text = stringResource(id = R.string.send),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
@Composable
fun ValidationTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    focusManager: FocusManager = LocalFocusManager.current,
    viewModel: SettlementRequestViewModel,
    value: String = "",
    onValueChange: (String) -> Unit,
    placeHolder: String,
    singleLine: Boolean = true,
    validationType: ValidationType,
    keyBoardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
) {
    val updatedText by viewModel.text
    var textState by remember { mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length))) }
    var isError by remember { mutableStateOf(viewModel.validate(textState.text, validationType).isError) }
    var errorMessage by remember { mutableStateOf(viewModel.validate(textState.text, validationType).errorMessage) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .focusRequester(focusRequester)
            .background(SearchBarBackground),
        value = textState,
        onValueChange = {
            onValueChange(it.text)
            viewModel.validateStartWithZeroOrDecimalPoint(it.text)
            textState = TextFieldValue(text = updatedText, selection = TextRange(updatedText.length))
            isError = viewModel.validate(it.text, validationType).isError
            errorMessage = viewModel.validate(it.text, validationType).errorMessage
        },
        isError = isError,
        textStyle = TextStyle(fontSize = 16.sp),
        placeholder = {
            Text(
                modifier = modifier,
                text = placeHolder,
                style = TextStyle(fontSize = 16.sp),
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
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
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