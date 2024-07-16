package com.bitaqaty.reseller.ui.presentation.applyFilter


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.BebeBlue
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.LightGrey400


@Composable
fun ApplyFilterScreen(navController: NavController, modifier: Modifier) {
    val notificationViewModel: ApplyFilterViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
    }
    ApplyFilter(onApplyFilterClick = {
        navController.popBackStack()
    })
}


@Composable
fun ApplyFilter(onApplyFilterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White)

            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column {
            DynamicSelectTextField()
            TextFiledd()
            DynamicSelectTextField()
            DynamicSelectTextField()
            DynamicSelectTextField()
            DynamicSelectTextField()
            DynamicSelectTextField()
            CheckBox()
        }
        Column {
            FilterButton(
                backgroundTex = BebeBlue, text = "Filter",
                iconVisibility = true,
                textColor = Color.White, onApplyFilterClick = {
                    onApplyFilterClick.invoke()
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextFiledd() {
    Card(
        Modifier
            .padding(
                vertical = Dimens.halfDefaultMargin, horizontal = Dimens.DefaultMargin,
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            value = "esfddssdsad",
            textStyle = TextStyle(
                color = BebeBlue,
                textAlign = TextAlign.Center
            ),
            onValueChange = { },
            shape = RoundedCornerShape(8.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                cursorColor = Color.Red,
                disabledLabelColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(

) {
    val options: List<String> =
        listOf("Account username", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Card(
        Modifier
            .padding(
                vertical = Dimens.halfDefaultMargin, horizontal = Dimens.DefaultMargin,
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        border = BorderStroke(Dimens.DefaultMargin0, BebeBlue),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
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
                    textAlign = TextAlign.Center
                ),
                onValueChange = { },
                trailingIcon = {
                    Image(
                        modifier = Modifier
                            .weight(1f),
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = ""
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    cursorColor = Color.Red,
                    disabledLabelColor = Color.Blue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),

                )
            ExposedDropdownMenu(
                expanded = expanded,
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
                                    .background(Color.Transparent)
                                    .padding(vertical = Dimens.fourDefaultMargin),
                                text = selectionOption,
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = Color.Red
                                )
                            )
                        },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false

                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CheckBox() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = true, onCheckedChange = {

            },
            colors = CheckboxDefaults.colors(
                uncheckedColor = BebeBlue,
                checkmarkColor = Color.White,
                checkedColor = BebeBlue,
            )
        )

        Text(text = "Show Total")
    }
}


@Composable
fun FilterButton(
    backgroundTex: Color, text: String,
    iconVisibility: Boolean, textColor: Color,
    haveBorder: Boolean = false,
    icon:Int=R.drawable.ic_filter_white,
    onApplyFilterClick: () -> Unit
) {
    var borderStroke: BorderStroke? = null
    if (haveBorder)
        borderStroke = BorderStroke(Dimens.DefaultMargin0, LightGrey400)
    Card(
        Modifier
            .clickable {
                onApplyFilterClick.invoke()
            }
            .padding(
                vertical = Dimens.halfDefaultMargin,
                horizontal = Dimens.DefaultMargin,
            )
            .fillMaxWidth(),
        border = borderStroke,
        shape = RoundedCornerShape(Dimens.halfDefaultMargin),
        colors = CardDefaults.cardColors(containerColor = backgroundTex)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.DefaultMargin,
                    vertical = Dimens.DefaultMargin20
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
                modifier = Modifier.padding(start = Dimens.halfDefaultMargin),
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
