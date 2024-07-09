package com.bitaqaty.reseller.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitaqaty.reseller.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_title,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Popular : Screen("popular_screen")
    object TopRated : Screen("top_rated_screen")
    object Upcoming : Screen("upcoming_screen")
    object NavigationDrawer :
        Screen("navigation_drawer", objectName = "genreId", objectPath = "/{genreId}")

    object MovieDetail :
        Screen("movie_detail_screen", objectName = "movieItem", objectPath = "/{movieItem}")

    object ArtistDetail :
        Screen(
            "artist_detail_screen", objectName = "artistId",
            objectPath = "/{artistId}"
        )

    // Bottom Navigation
    object HomeNav : Screen("home_screen", title = R.string.home, navIcon = {
        Icon(
            painterResource(R.drawable.icon_home),
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object PopularNav : Screen("popular_screen", title = R.string.popular, navIcon = {
        Icon(
            painterResource(R.drawable.icon_store),
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object TopRatedNav : Screen("top_rated_screen", title = R.string.top_rate, navIcon = {
        Icon(
            painterResource(R.drawable.icon_fav),
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object UpcomingNav : Screen("upcoming_screen",
        title = R.string.up_coming, navIcon = {
            Icon(
                painterResource(R.drawable.icon_search),
                contentDescription = "search",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(x = 10.dp)
            )
        })
}