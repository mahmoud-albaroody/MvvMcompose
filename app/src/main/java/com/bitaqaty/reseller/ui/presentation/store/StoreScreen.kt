package com.bitaqaty.reseller.ui.presentation.store

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.ui.presentation.common.Loading
import com.bitaqaty.reseller.ui.presentation.components.SearchCategory
import com.bitaqaty.reseller.ui.theme.frutigerLTArabic
import com.bitaqaty.reseller.utilities.network.DataState

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    viewModel: StoreViewModel = hiltViewModel(),
    navController: NavController,
){
    val categoryState by viewModel.categoryState

    LaunchedEffect(key1 = true) {
        viewModel.getCategoryList()
    }

    when(categoryState){
        is DataState.Loading -> Loading()
        is DataState.Error -> {
            val error = (categoryState as DataState.Error).exception
            Text(text = "Error: ${error.message}")
        }
        is DataState.Success -> {
            val categoryList = (categoryState as DataState.Success<ArrayList<Category>>).data
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(12.dp)
            ){
                items(categoryList){ category ->
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Min),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchCategory(category)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = category.getName(),
                            fontFamily = frutigerLTArabic,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

//@Composable
//@Preview(showSystemUi = true)
//fun StoreScreenPreview(){
//    StoreScreen()
//}