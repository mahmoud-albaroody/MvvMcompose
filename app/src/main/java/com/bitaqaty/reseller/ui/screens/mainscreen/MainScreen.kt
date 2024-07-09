package com.bitaqaty.reseller.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.model.Genres
import com.bitaqaty.reseller.data.model.moviedetail.Genre
import com.bitaqaty.reseller.navigation.Navigation
import com.bitaqaty.reseller.navigation.Screen
import com.bitaqaty.reseller.navigation.currentRoute
import com.bitaqaty.reseller.ui.component.CircularIndeterminateProgressBar
import com.bitaqaty.reseller.ui.component.SearchUI
import com.bitaqaty.reseller.ui.screens.drawer.DrawerUI
import com.bitaqaty.reseller.utils.AppConstant
import com.bitaqaty.reseller.utils.network.DataState
import com.bitaqaty.reseller.utils.networkconnection.ConnectionState
import com.bitaqaty.reseller.utils.networkconnection.connectivityState
import com.bitaqaty.reseller.utils.pagingLoadingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()
    //    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }


    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    // genre api call for first time
    LaunchedEffect(key1 = 0) {
        delay(1000)
        mainViewModel.genreList()
    }


    if (mainViewModel.genres.value is DataState.Success<Genres>) {
        genreList.value =
            (mainViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList
        // All first value as all
        if (genreList.value.first().name != AppConstant.DEFAULT_GENRE_ITEM)
            genreList.value.add(0, Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    }
    Scaffold(scaffoldState = scaffoldState,
//            topBar = {
//                when (currentRoute(navController)) {
//                    Screen.Home.route,
//                    Screen.Popular.route,
//                    Screen.TopRated.route,
//                    Screen.Upcoming.route,
//                    Screen.NavigationDrawer.route -> {
//                        if (isAppBarVisible.value) {
//                            val appTitle: String =
//                                if (currentRoute(navController) == Screen.NavigationDrawer.route) genreName.value
//                                else stringResource(R.string.app_title)
//                            HomeAppBar(title = appTitle, openDrawer = {
//                                scope.launch {
//                                    scaffoldState.drawerState.apply {
//                                        if (isClosed) open() else close()
//                                    }
//                                }
//                            }, openFilters = {
//                                isAppBarVisible.value = false
//                            })
//                        } else {
//                            SearchBar(isAppBarVisible, mainViewModel)
//                        }
//                    }
//
//                    else -> {
//                        AppBarWithArrow(navigationTitle(navController)) {
//                            navController.popBackStack()
//                        }
//                    }
//                }
//            },

        drawerContent = {
            // Drawer content
            DrawerUI(navController, genreList.value as List<Genre>) {
                genreName.value = it
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
//        floatingActionButton = {
//            when (currentRoute(navController)) {
//                Screen.Home.route, Screen.Popular.route,
//                Screen.TopRated.route, Screen.Upcoming.route -> {
//                    FloatingActionButton(
//                        onClick = {
//                            isAppBarVisible.value = false
//                        },
//                        backgroundColor = SecondaryFontColor
//                    ) {
//                        Icon(Icons.Filled.Search, "", tint = Color.White)
//                    }
//                }
//            }
//        },
        bottomBar = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                    BottomNavigationUI(navController)
                }
            }
        },
        snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {}, modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Navigation(navController, Modifier.padding(it), genreList.value)
            Column {
                CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                if (isAppBarVisible.value.not()) {
                    SearchUI(navController, mainViewModel.searchData) {
                        isAppBarVisible.value = true
                    }
                }
            }
        }
        mainViewModel.searchData.pagingLoadingState {
            searchProgressBar.value = it
        }
    }
}


@Composable
fun BottomNavigationUI(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        val items = listOf(
            Screen.HomeNav,
            Screen.PopularNav,
            Screen.TopRatedNav,
            Screen.UpcomingNav,
            Screen.HomeNav,
            Screen.HomeNav,
        )
        items.forEachIndexed { index, item ->
            BottomNavigationItem(

                label = {
                    Text(
                        text = stringResource(id = item.title),
                        fontSize = 7.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                },
                selected = currentRoute(navController) == item.route,
                icon = item.navIcon,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray.copy(0.4f),
                modifier = Modifier.tabIndicator(selectedTabIndex == index),
                        onClick = {
                            selectedTabIndex = index
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
                        restoreState = true
                  }
                })
        }

    }
}

@Composable
fun Modifier.tabIndicator(selected: Boolean): Modifier = composed {
    if (selected) {
        this.then(
            Modifier.padding(bottom = 2.dp)
                .background(Color.White, RectangleShape)
        )
    } else {
        this
    }
}