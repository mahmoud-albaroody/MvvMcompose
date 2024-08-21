package com.bitaqaty.reseller.ui.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.ui.presentation.search.SearchViewModel
import com.bitaqaty.reseller.ui.theme.Dimens
import com.bitaqaty.reseller.ui.theme.PlaceHolder
import com.bitaqaty.reseller.ui.theme.SearchBarBackground
import com.bitaqaty.reseller.ui.theme.SearchBarText
import com.bitaqaty.reseller.ui.theme.border
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: SearchViewModel
) {
    var query by viewModel.query

    LaunchedEffect(query){
        if(query.length >= 3){
            snapshotFlow { query }
                .debounce(500)
                .collect { debouncedQuery ->
                    val productsInfo = ProductListRequest(
                        searchCriteria = debouncedQuery
                    )
                    viewModel.getSearchProducts(productsInfo)
                }
        }
    }

    TextField(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(
                //top = Dimens.padding30,
                start = Dimens.padding12,
                end = Dimens.padding12
            )
            .background(SearchBarBackground)
            .border(
                Dimens.borderThickness3,
                border,
                MaterialTheme.shapes.medium
            ),
        value = query,
        onValueChange = { query = it },
        textStyle = MaterialTheme.typography.PlaceHolder,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
            )
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
            focusedTextColor = Color.Black,
            focusedContainerColor = SearchBarBackground,
            unfocusedContainerColor = SearchBarBackground,
            disabledContainerColor = SearchBarBackground,
            cursorColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
    )
}

//@Preview()
//@Composable
//fun SearchBarPreview() {
//    SearchBar()
//}