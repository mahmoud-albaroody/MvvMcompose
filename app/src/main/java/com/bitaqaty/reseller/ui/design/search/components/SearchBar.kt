package com.bitaqaty.reseller.ui.design.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.PlaceHolder
import com.bitaqaty.reseller.ui.theme.SearchBarBackground
import com.bitaqaty.reseller.ui.theme.SearchBarText
import com.bitaqaty.reseller.ui.theme.border


@Composable
fun SearchBar() {
    val text = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(
                top = Dimens.padding30,
                start = Dimens.padding12,
                end = Dimens.padding12
            )
            .background(SearchBarBackground)
            .border(
                Dimens.borderThickness3,
                border,
                MaterialTheme.shapes.medium
            )
            .padding(Dimens.padding12),
        value = text.value,
        onValueChange = { text.value = it },
        textStyle = MaterialTheme.typography.PlaceHolder,
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_place_holder),
                style = MaterialTheme.typography.PlaceHolder,
                color = SearchBarText,
                maxLines = 1
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchBarBackground,
            unfocusedContainerColor = SearchBarBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            cursorColor = Color.Gray
        ),
        singleLine = true
    )
}

@Preview(showBackground = false)
@Composable
fun SearchBarPreview() {
    SearchBar()
}