package com.bitaqaty.reseller.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Divider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.ui.theme.BitaqatyTheme
import com.bitaqaty.reseller.ui.theme.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorite,
        Screen.Store,
        Screen.Transactions,
        Screen.More
    )

    val selectedItem = remember { mutableIntStateOf(0) }
    val currentRoute = remember { mutableStateOf(Screen.Home.route) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRoute.value) {
            selectedItem.intValue = index
        }
    }

    NavigationBar(containerColor = Color.White, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            items.forEachIndexed { index, item ->

                val isSelected = index == selectedItem.intValue

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        this@NavigationBar.BottomNavigationItem(
                            alwaysShowLabel = true,
                            icon = {
                                Image(
                                    painter = painterResource(item.icon),
                                    contentDescription = item.title,
                                    modifier = Modifier
                                        .height(Dimens.imageSize)
                                        .width(Dimens.imageSize),
                                    colorFilter = if (isSelected) {
                                        ColorFilter.tint(colorResource(id = R.color.purple_700))
                                    } else {
                                        ColorFilter.tint(colorResource(id = R.color.grey))
                                    }
                                )
                            },
                            label = {
                                Text(
                                    modifier = if(isSelected){
                                        Modifier.basicMarquee(initialDelayMillis = 0)
                                                             }else{
                                        Modifier
                                                                  },
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = item.title,
                                    fontSize = Dimens.fontSize,
                                    fontWeight = if (isSelected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                selectedItem.intValue = index
                                currentRoute.value = item.route
                                navController.navigate(item.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selectedContentColor = colorResource(R.color.purple_700),
                            unselectedContentColor = colorResource(R.color.grey),
                        )

                        if (isSelected) {
                            Divider(
                                color = colorResource(id = R.color.purple_700),
                                thickness = Dimens.thickness,
                                modifier = Modifier
                                    .width(Dimens.width)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BitaqatyTheme {
        BottomNavigationBar(navController = rememberNavController())
    }
}
