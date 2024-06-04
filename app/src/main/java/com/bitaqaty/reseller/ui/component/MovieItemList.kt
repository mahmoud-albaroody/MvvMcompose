package com.bitaqaty.reseller.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bitaqaty.reseller.R
import com.bitaqaty.reseller.data.datasource.remote.ApiURL
import com.bitaqaty.reseller.data.model.HomeMenuItem
import com.bitaqaty.reseller.data.model.MovieItem
import com.bitaqaty.reseller.data.model.moviedetail.Genre
import com.bitaqaty.reseller.navigation.Screen
import com.bitaqaty.reseller.navigation.currentRoute
import com.bitaqaty.reseller.ui.theme.DefaultBackgroundColor
import com.bitaqaty.reseller.ui.theme.Purple40
import com.bitaqaty.reseller.ui.theme.Purple80
import com.bitaqaty.reseller.ui.theme.SecondaryFontColor
import com.bitaqaty.reseller.ui.theme.cornerRadius
import com.bitaqaty.reseller.utils.conditional
import com.bitaqaty.reseller.utils.items
import com.bitaqaty.reseller.utils.pagingLoadingState
import com.bitaqaty.reseller.utils.toPrettyJson
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieItemList(
    navController: NavController,
    movies: Flow<PagingData<MovieItem>>,
    genres: ArrayList<Genre>? = null,
    selectedName: Genre?,
    onclick: (genreId: Genre?) -> Unit
) {
    val configuration = LocalConfiguration.current
    val homeMenuItems: ArrayList<HomeMenuItem> = arrayListOf()
    homeMenuItems.add(HomeMenuItem(R.drawable.cat1, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat2, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat1, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat2, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat1, "sdsdsd", false))

    homeMenuItems.add(HomeMenuItem(R.drawable.cat2, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat1, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat2, "sdsdsd", false))
    homeMenuItems.add(HomeMenuItem(R.drawable.cat1, "sdsdsd", false))
    var sizeState by remember { mutableStateOf(100.dp) }
    val sizee by animateDpAsState(
        targetValue = sizeState, tween(
            durationMillis = 10,
        ), label = ""
    )
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val moviesItems: LazyPagingItems<MovieItem> = movies.collectAsLazyPagingItems()

    var selectedIndex by remember { mutableStateOf(-1) }
    BackHandler(enabled = (currentRoute(navController) == Screen.Home.route)) {
        openDialog.value = true
    }
    var enter: EnterTransition = expandVertically() + fadeIn()
    var exit: ExitTransition = fadeOut() + shrinkVertically()
    var v by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()


    Row(modifier = Modifier.background(DefaultBackgroundColor)) {

        Column(
            modifier = Modifier
                .background(Purple80)
                .width((screenWidth / 5).dp)
                .height(screenHeight)
        ) {
//            AnimatedInfiniteLazyColumn(
//                items = homeMenuItems,
//                visibleItemCount =2,
//                inactiveColor = InactiveColor,
//                activeColor = ActiveColor,
//                selectorIndex = 0,
//                inactiveItemPercent = 70,
//                itemContent = { animationProgress, index, item, height, lazyListState ->
//
//                    val color = animationProgress.color
//                    val scale = animationProgress.scale
//
//                    Box(
//                        modifier = Modifier
//                            .scale(scale)
//                            .background(color, CircleShape)
//                            .size(height)
//                            .clickable(
//                                interactionSource = remember {
//                                    MutableInteractionSource()
//                                },
//                                indication = null
//                            ) {
//                                coroutineScope.launch {
//                                    lazyListState.animateScrollBy(animationProgress.distanceToSelector)
//                                }
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//
//                        androidx.compose.material3.Text(
//                            "$index",
//                            color = Color.White,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_title)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp, end = 5.dp)
            )
            {
                itemsIndexed(items = homeMenuItems) { index, item ->
                    HomeItemView(
                        item,
                        selectedItem = selectedIndex == index,
                        navController,
                        sizee.value,
                        clickAction = {
                            selectedIndex = index
                        })
                }

            }
        }
        Column(
            modifier = Modifier
                .background(DefaultBackgroundColor)
                .fillMaxWidth()
        ) {
            genres?.let {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp, start = 9.dp, end = 9.dp)
                        .fillMaxWidth()
                ) {
                    items(genres) { item ->
                        SelectableGenreChip(
                            selected = item.name === selectedName?.name,
                            genre = item.name,
                            onclick = {
                                onclick(item)
                            }
                        )
                    }
                }
            }
            CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .conditional(genres == null) {
                        padding(top = 8.dp)
                    },
                content = {
                    items(moviesItems) { item ->
                        item?.let {
                            MovieItemView(item, navController)
                        }
                    }
                })
        }
    }

    if (openDialog.value) {
        ExitAlertDialog(navController, {
            openDialog.value = it
        }, {
            activity?.finish()
        })

    }
    moviesItems.pagingLoadingState {
        progressBar.value = it
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {

//    val roundedTopCornerShape = RoundedCornerShape(
//        topStart = CornerSize(0.dp), // adjust the radius as needed
//        topEnd = CornerSize(16.dp),   // adjust the radius as needed
//        bottomStart = CornerSize(16.dp),
//        bottomEnd = CornerSize(16.dp)
//    )
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        Card(
            modifier = Modifier
                .size(250.dp),
//            shape = roundedTopCornerShape,
        ) {
            CoilImage(
                modifier = Modifier
                    .size(200.dp)
//                    .clip(shape = roundedTopCornerShape)
                    .clickable {
                        //  sheetState.expand()
                        // navController.navigate(Screen.MovieDetail.route.plus("/${item.id}"))
                    },
                imageModel = { ApiURL.IMAGE_URL.plus(item.posterPath) },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = "Movie item",
                    colorFilter = null,
                ),
                component = rememberImageComponent {
                    +CircularRevealPlugin(
                        duration = 200
                    )
                    +ShimmerPlugin(
                        baseColor = SecondaryFontColor,
                        highlightColor = DefaultBackgroundColor
                    )
                },
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "12.25",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Text(text = "SAR", style = TextStyle(fontSize = 10.sp))
            }
        }
    }
}

@Composable
fun SelectableGenreChip(
    selected: Boolean,
    genre: String,
    onclick: () -> Unit
) {

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Purple80 else Purple40,
        animationSpec = tween(
            durationMillis = 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .cornerRadius(4)
            .background(
                color = animateChipBackgroundColor
            )
            .height(62.dp)
            .widthIn(min = 80.dp)
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            }
    ) {
        Row(
            Modifier.align(Alignment.Center)        ,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            CoilImage(
//                modifier = Modifier
//                    .size(24.dp)
//                    .cornerRadius(24),
//                imageModel = { "https://image.tmdb.org/t/p/w342/ktHEdqmMWC1wdfPRMRCTZe2OISL.jpg" },
//                imageOptions = ImageOptions(
//                    contentScale = ContentScale.Crop,
//                    alignment = Alignment.Center,
//                    contentDescription = "Movie item",
//                    colorFilter = null,
//                ),
//                component = rememberImageComponent {
//                    +CircularRevealPlugin(
//                        duration = 800
//                    )
//                    +ShimmerPlugin(
//                        baseColor = SecondaryFontColor,
//                        highlightColor = DefaultBackgroundColor
//                    )
//                },
//            )

            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.stc),
                contentDescription = ""
            )
            Text(
                text = genre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = Color.White.copy(alpha = 0.80F)
            )
        }
    }


}

@Composable
fun HomeItemView(
    item: HomeMenuItem,
    selectedItem: Boolean,
    navController: NavController,
    size: Float,
    clickAction: () -> Unit
) {

    Box(
        Modifier
            .clickable {
                clickAction()

            }
            .background(
                if (selectedItem) Color.Blue else SecondaryFontColor,
            )
            .size(if (selectedItem) (size * 1.1).dp else size.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 0.dp),
        ) {

            Image(
                painter = painterResource(id = item.image),
                contentDescription = stringResource(id = R.string.app_title)
            )
//            CoilImage(
//                modifier = Modifier
//                    .size(60.dp),
////                .cornerRadius(24)
//                imageModel = { ApiURL.IMAGE_URL.plus(item.image) },
//                imageOptions = ImageOptions(
////                contentScale = ContentScale.Crop,
//                    alignment = Alignment.Center,
//                    contentDescription = "Movie item",
//                    colorFilter = null,
//                ),
////            component = rememberImageComponent {
////                +CircularRevealPlugin(
////                    duration = 800
////                )
////                +ShimmerPlugin(
////                    baseColor = SecondaryFontColor,
////                    highlightColor = DefaultBackgroundColor
////                )
////            },
//            )
//            Text(
//                text = item.title, fontSize = 14.sp,
//                fontWeight = FontWeight.Light,
//                textAlign = TextAlign.Center,
//                color = Color.Black.copy(alpha = 0.80F),
//                style = TextStyle(background = Color.Yellow),
//            )
        }
//        }
    }

}




