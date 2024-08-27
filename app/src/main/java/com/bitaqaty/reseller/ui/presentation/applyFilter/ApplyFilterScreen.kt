package com.bitaqaty.reseller.ui.presentation.applyFilter


import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.Product
import com.bitaqaty.reseller.data.model.RechargeMethod
import com.bitaqaty.reseller.ui.theme.BanKTransferBackgroundColor
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Blue100
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.FontColor
import com.bitaqaty.reseller.ui.theme.LightGrey400
import com.bitaqaty.reseller.ui.theme.Transparent
import com.bitaqaty.reseller.ui.theme.White
import com.bitaqaty.reseller.utilities.Utils
import com.bitaqaty.reseller.utilities.extention.toJson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


@Composable
fun ApplyFilterScreen(
    navController: NavController,
    modifier: Modifier,
    comeFrom: String
) {
    val applyFilterViewModel: ApplyFilterViewModel = hiltViewModel()
    val simpleCategoryList = remember { mutableStateListOf<Category>() }
    val simpleMerchantList = remember { mutableStateListOf<Merchant>() }
    val simpleProductList = remember { mutableStateListOf<Product>() }
    val rechargeMethodsList = remember { mutableStateListOf<RechargeMethod>() }
    LaunchedEffect(key1 = true) {
        applyFilterViewModel.getSimpleCategoryList()
        applyFilterViewModel.getSurePayRechargeMethods()
        applyFilterViewModel.getUserNamesList()

        applyFilterViewModel.viewModelScope.launch {
            applyFilterViewModel.getSurePayRechargeMethods.collect {
                rechargeMethodsList.addAll(it)
            }
        }
        applyFilterViewModel.viewModelScope.launch {
            applyFilterViewModel.getSimpleCategoryList.collect {
                simpleCategoryList.addAll(it)
            }
        }
        applyFilterViewModel.viewModelScope.launch {
            applyFilterViewModel.getSimpleMerchantList.collect {
                simpleMerchantList.clear()
                simpleMerchantList.addAll(it)

            }
        }
        applyFilterViewModel.viewModelScope.launch {
            applyFilterViewModel.getProductLookList.collect {
                simpleProductList.clear()
                simpleProductList.addAll(it)
            }
        }
    }
    ApplyFilter(simpleCategoryList.toList(),
        simpleMerchantList, simpleProductList, rechargeMethodsList,
        viewModel = applyFilterViewModel,
        comeFrom = comeFrom,
        onApplyFilterClick = { accountNo, categoryId,
                               merchantId, productId,
                               channel, paymentMethod,
                               searchPeriod,
                               selectedDateFrom,
                               selectedDateTo,
                               isChecked, serialNo, pinCode, isPrinted, discrmenationVal
            ->

            val jsonObject = JsonObject()
            jsonObject.addProperty("accountNo", accountNo)
            jsonObject.addProperty("categoryId", categoryId)
            jsonObject.addProperty("merchantId", merchantId)
            jsonObject.addProperty("productId", productId)
            jsonObject.addProperty("channel", channel)
            jsonObject.addProperty("searchPeriod", searchPeriod)
            jsonObject.addProperty("paymentMethod", paymentMethod)
            jsonObject.addProperty("selectedDateTo", selectedDateTo)
            jsonObject.addProperty("selectedDateFrom", selectedDateFrom)
            jsonObject.addProperty("isChecked", isChecked)
            jsonObject.addProperty("serialNo", serialNo)
            jsonObject.addProperty("pinCode", pinCode)
            jsonObject.addProperty("isPrinted", isPrinted)
            jsonObject.addProperty("showTotal", isChecked)
            jsonObject.addProperty("discrmenationVal", discrmenationVal)

            navController.popBackStack()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("filterObject", jsonObject.toJson())

        })

}


@Composable
fun ApplyFilter(
    simpleCategoryList: List<Category>,
    simpleMerchantList: List<Merchant>,
    simpleProductList: List<Product>,
    rechargeMethodsList: List<RechargeMethod>,
    viewModel: ApplyFilterViewModel, comeFrom: String,
    onApplyFilterClick: (
        Int, Int, Int, Int, String,
        String, String, String, String,
        Boolean, String, String, String, String
    ) -> Unit
) {
    val context = LocalContext.current
    val accountName = arrayListOf<String>()
    accountName.add(stringResource(id = R.string.btrr_account_name))
    Utils.getUserData()?.reseller?.username?.let { accountName.add(it) }


    val categoryList = arrayListOf<String>()
    categoryList.add(stringResource(id = R.string.category))
    if (simpleCategoryList.isNotEmpty())
        simpleCategoryList.forEach {
            it.getName()?.let { it1 -> categoryList.add(it1) }
        }
    val serviceList = arrayListOf<String>()
    serviceList.add(stringResource(id = R.string.service))
    if (simpleMerchantList.isNotEmpty())
        simpleMerchantList.forEach {
            it.getName().let { it1 -> serviceList.add(it1) }
        }
    val productList = arrayListOf<String>()
    productList.add(stringResource(id = R.string.productName))
    if (simpleProductList.isNotEmpty())
        simpleProductList.forEach {
            it.getName().let { it1 -> productList.add(it1) }
        }
    val methodList = arrayListOf<String>()
    methodList.add(stringResource(id = R.string.payment_method))
    if (rechargeMethodsList.isNotEmpty())
        rechargeMethodsList.forEach {
            it.getName().let { it1 -> methodList.add(it1) }
        }
    val dateList = arrayListOf<String>()
    dateList.add(stringResource(id = R.string.report_this_month))
    dateList.add(stringResource(id = R.string.report_last_month))
    dateList.add(stringResource(id = R.string.report_last_week))
    dateList.add(stringResource(id = R.string.report_yesterday))
    dateList.add(stringResource(id = R.string.report_today))
    dateList.add(stringResource(id = R.string.report_custom_date))

    val channelList = arrayListOf<String>()
    channelList.add(stringResource(id = R.string.report_channel))
    channelList.add(stringResource(id = R.string.report_all))
    channelList.add(stringResource(id = R.string.report_portal))
    channelList.add(stringResource(id = R.string.report_pos))
    channelList.add(stringResource(id = R.string.report_integration))
    channelList.add(stringResource(id = R.string.report_wallet))

    val printedList = arrayListOf<String>()
    printedList.add(stringResource(id = R.string.printed))
    printedList.addAll(stringArrayResource(id = R.array.printed_arr))

    var viewCustomDate by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    var categoryId by remember { mutableIntStateOf(0) }
    var merchantId by remember { mutableIntStateOf(0) }
    var productId by remember { mutableIntStateOf(0) }
    var channel by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var searchPeriod by remember { mutableStateOf("") }
    var isPrinted by remember { mutableStateOf("") }
    var subAccountId by remember { mutableIntStateOf(0) }
    var serialNo by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var discrmenationVal by remember { mutableStateOf("") }
    var selectedDateTo by remember {
        mutableStateOf("")
    }
    var selectedDateFrom by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column {
            if (comeFrom == "SalesReport" || comeFrom == "TransactionLog")
                DynamicSelectTextField(
                    TextAlign.Center,
                    accountName, true
                ) {
                    if (it == Utils.getUserData()?.reseller?.username.toString()) {
                        subAccountId = Utils.getUserData()?.reseller?.id!!
                    }
                }
            if (comeFrom == "TransactionLog") {
                TextFiledd(stringResource(id = R.string.TLogSerialNo)) {
                    serialNo = it
                }
                TextFiledd(stringResource(id = R.string.TLogPin)) {
                    pinCode = it
                }
            }
            if (comeFrom == "SalesReport" || comeFrom == "productDiscount")
                DynamicSelectTextField(
                    TextAlign.Center,
                    categoryList, true
                ) { selectedName ->
                    categoryId = simpleCategoryList.find { it.getName() == selectedName }?.id!!
                    viewModel.getSimpleMerchantList(categoryId)
                }

            if (comeFrom == "SalesReport" || comeFrom == "productDiscount")
                DynamicSelectTextField(
                    TextAlign.Center,
                    serviceList, true
                ) { selectedName ->
                    merchantId = simpleMerchantList.find { it.getName() == selectedName }?.id!!
                    viewModel.getProductLookList(
                        categoryId = categoryId,
                        merchantId = merchantId
                    )

                }
            if (comeFrom == "SalesReport")
                DynamicSelectTextField(
                    TextAlign.Center,
                    productList, true
                ) { selectedProduct ->
                    productId = simpleProductList.find { it.getName() == selectedProduct }?.id!!
                }

            if (comeFrom == "SalesReport" || comeFrom == "TransactionLog")
                DynamicSelectTextField(
                    TextAlign.Center,
                    channelList, true
                ) {
                    channel = it
                }
            if (comeFrom == "TransactionLog")
                DynamicSelectTextField(
                    TextAlign.Center,
                    printedList, true
                ) {
                    isPrinted = it
                }
            if (comeFrom != "productDiscount")
                DynamicSelectTextField(
                    TextAlign.Center,
                    methodList, true
                ) { payment ->
                    paymentMethod = payment
                    discrmenationVal =
                        rechargeMethodsList.find { it.getName() == payment }?.discriminatorValue.toString()

                }
            if (comeFrom != "productDiscount")
                DynamicSelectTextField(
                    TextAlign.Center,
                    dateList, true
                ) {
                    if (it == context.getString(R.string.report_custom_date)) {
                        viewCustomDate = true
                    } else {
                        viewCustomDate = false
                        searchPeriod = it
                    }

                }
            if (viewCustomDate) {
                CustomDate(dateTo = {
                    selectedDateTo = it
                }, dateFrom = {
                    selectedDateFrom = it
                })
            }
            if (comeFrom != "rechargeLog" && comeFrom != "productDiscount")
                CheckBox {
                    isChecked = it
                }
        }
        Column {
            FilterButton(
                backgroundTex = Blue100,
                text = stringResource(id = R.string.filter),
                iconVisibility = true,
                textColor = White,
                horizontalPadding = Dimens.DefaultMargin,
                onApplyFilterClick = {
                    onApplyFilterClick(
                        subAccountId, categoryId,
                        merchantId, productId,
                        channel,
                        paymentMethod, searchPeriod,
                        selectedDateFrom, selectedDateTo,
                        isChecked, serialNo, pinCode, isPrinted, discrmenationVal
                    )
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFiledd(title: String, onEnterString: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var viewPlaceholder by remember {
        mutableStateOf(true)
    }
    Card(
        Modifier
            .padding(
                vertical = Dimens.padding4,
                horizontal = Dimens.DefaultMargin,
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        val focusRequester = FocusRequester()

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    viewPlaceholder = !it.isFocused
                },
            value = text,
            textStyle = TextStyle(
                color = BebeBlue,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                if (viewPlaceholder)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = BebeBlue
                    )
            },
            onValueChange = {
                text = it
                onEnterString(it)
            },
            shape = RoundedCornerShape(8.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                cursorColor = BebeBlue,
                disabledLabelColor = BebeBlue,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                unfocusedContainerColor = Transparent,
                focusedContainerColor = Transparent,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    textAlign: TextAlign,
    options: List<String>,
    clickable: Boolean,
    onSelectItem: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Card(
        Modifier
            .padding(
                vertical = Dimens.padding4,
                horizontal = Dimens.DefaultMargin,
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                textStyle = TextStyle(
                    color = BebeBlue,
                    textAlign = textAlign
                ),
                onValueChange = { },
                trailingIcon = {
                    Image(
                        modifier = Modifier
                            .weight(1f),
                        painter = painterResource
                            (R.drawable.ic_arrow_down),
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    cursorColor = BebeBlue,
                    disabledLabelColor = BebeBlue,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    unfocusedContainerColor = Transparent,
                    focusedContainerColor = Transparent,
                ),

                )
            ExposedDropdownMenu(
                expanded = expanded,
                modifier = Modifier.background(White),
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(White),
                                text = selectionOption,
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = FontColor
                                )
                            )
                        },
                        onClick = {
                            if (clickable) {
                                selectedOptionText = selectionOption
                                expanded = false
                                onSelectItem(selectedOptionText)
                            }

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CheckBox(isChecked: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked, onCheckedChange = {
                checked = !checked
                isChecked(checked)
            },
            colors = CheckboxDefaults.colors(
                uncheckedColor = BebeBlue,
                checkmarkColor = Color.White,
                checkedColor = BebeBlue,
            )
        )

        Text(text = stringResource(id = R.string.show_total))
    }
}


@Composable
fun FilterButton(
    backgroundTex: Color, text: String,
    iconVisibility: Boolean, textColor: Color,
    haveBorder: Boolean = false,
    icon: Int = R.drawable.ic_filter_white, horizontalPadding: Dp,
    onApplyFilterClick: () -> Unit
) {
    var borderStroke: BorderStroke? = null
    if (haveBorder)
        borderStroke = BorderStroke(Dimens.DefaultMargin0, LightGrey400)
    Card(
        Modifier

            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = horizontalPadding,
            )
            .fillMaxWidth(),
        border = borderStroke,
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        colors = CardDefaults.cardColors(containerColor = backgroundTex)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onApplyFilterClick.invoke()
                }
                .padding(
                    horizontal = Dimens.DefaultMargin,
                    vertical = Dimens.padding16
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (iconVisibility)
                Image(
                    painter = painterResource(icon),
                    contentDescription = ""
                )

            Text(
                modifier = Modifier
                    .padding(start = Dimens.halfDefaultMargin),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = 14.sp,
                ),
                text = text
            )
        }
    }
}


@Composable
fun CustomDate(dateTo: (String) -> Unit, dateFrom: (String) -> Unit) {
    val mContext = LocalContext.current
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showTimerPicker by remember {
        mutableStateOf(false)
    }
    var switchItem by remember {
        mutableStateOf(true)
    }
    var deleteDateTo by remember {
        mutableStateOf(false)
    }
    var deleteDateFrom by remember {
        mutableStateOf(false)
    }
    var selectedDateTo by remember {
        mutableStateOf(mContext.getString(R.string.to))
    }
    var selectedDateFrom by remember {
        mutableStateOf(mContext.getString(R.string.from))
    }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            Modifier
                .weight(1f)
                .padding(
                    vertical = Dimens.halfDefaultMargin
                )
                .padding(start = Dimens.DefaultMargin, end = Dimens.halfDefaultMargin)
                .clickable {
                    showDatePicker = true
                    switchItem = true
                },
            shape = RoundedCornerShape(Dimens.DefaultMargin10),
            colors = CardDefaults.cardColors(containerColor = BanKTransferBackgroundColor)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            start = Dimens.halfDefaultMargin,
                            top = Dimens.halfDefaultMargin,
                            bottom = Dimens.halfDefaultMargin
                        )
                        .weight(1f),
                    painter = painterResource(R.drawable.ic_calendar_date_schedu),
                    contentDescription = ""
                )
                Text(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(
                            vertical = Dimens.DefaultMargin,
                            horizontal = Dimens.padding4
                        ),
                    text = selectedDateFrom,
                    style = TextStyle(
                        fontSize = Dimens.fontSize12,
                        textAlign = TextAlign.Center,
                        color = BebeBlue,
                    )
                )
                if (deleteDateFrom)
                    Image(
                        modifier = Modifier
                            .padding(
                                start = Dimens.halfDefaultMargin,
                                top = Dimens.halfDefaultMargin,
                                bottom = Dimens.halfDefaultMargin
                            )
                            .weight(1f)
                            .clickable {
                                selectedDateFrom = mContext.getString(R.string.from)
                                deleteDateFrom = false
                                dateFrom("")
                            },
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = ""
                    )
            }

        }
        Card(
            Modifier
                .weight(1f)
                .padding(
                    vertical = Dimens.halfDefaultMargin
                )
                .padding(start = Dimens.halfDefaultMargin, end = Dimens.DefaultMargin)
                .clickable {
                    showDatePicker = true
                    switchItem = false
                },
            shape = RoundedCornerShape(Dimens.DefaultMargin10),
            colors = CardDefaults.cardColors(containerColor = BanKTransferBackgroundColor)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            start = Dimens.halfDefaultMargin,
                            top = Dimens.halfDefaultMargin,
                            bottom = Dimens.halfDefaultMargin
                        )
                        .weight(1f),
                    painter = painterResource(R.drawable.ic_calendar_date_schedu),
                    contentDescription = ""
                )
                Text(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(
                            vertical = Dimens.DefaultMargin,
                            horizontal = Dimens.padding4
                        ),

                    text = selectedDateTo,
                    style = TextStyle(
                        fontSize = Dimens.fontSize12,
                        textAlign = TextAlign.Center,
                        color = BebeBlue,
                    )
                )
                if (deleteDateTo)
                    Image(
                        modifier = Modifier
                            .padding(
                                start = Dimens.halfDefaultMargin,
                                top = Dimens.halfDefaultMargin,
                                bottom = Dimens.halfDefaultMargin
                            )
                            .weight(1f)
                            .clickable {
                                selectedDateTo = mContext.getString(R.string.to)
                                deleteDateTo = false
                                dateTo("")
                            },
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = ""
                    )
            }
        }
    }
    if (showDatePicker)
        DatePickerModal(onDateSelected = {
            if (switchItem) {
                selectedDateFrom = it
                deleteDateFrom = true
            } else {
                selectedDateTo = it
                deleteDateTo = true
            }
            showDatePicker = false
            showTimerPicker = true
        }, onDismiss = {
            showDatePicker = false
        })

    if (showTimerPicker)
        TimePickerDialog(onTimeSelected = {
            showTimerPicker = false
            if (switchItem) {
                selectedDateFrom += " $it"
                deleteDateFrom = true
                dateFrom(selectedDateFrom)
            } else {
                selectedDateTo += " $it"
                deleteDateTo = true
                dateTo(selectedDateTo)
            }

        }, onDismiss = {
            showTimerPicker = false
        })
}


@Composable
fun DatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext, R.style.DatePickerTheme,
        { mDatePicker: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            onDateSelected(mDate.value)
        }, mYear, mMonth, mDay
    )

    mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis
    mDatePickerDialog.setOnDismissListener {
        onDismiss.invoke()
    }

    mDatePickerDialog.show()

}


@Composable
fun TimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mTime = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        mContext, R.style.DatePickerTheme,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
            onTimeSelected(mTime.value)
        }, mHour, mMinute, false
    )
    timePickerDialog.setOnDismissListener {
        onDismiss.invoke()
    }
    timePickerDialog.show()
}

