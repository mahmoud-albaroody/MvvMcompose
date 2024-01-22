package com.bitaqaty.reseller.ui.screens.bottomnavigation.nowplaying

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bitaqaty.reseller.data.model.GenreId
import com.bitaqaty.reseller.data.model.moviedetail.Genre
import com.bitaqaty.reseller.ui.component.MovieItemList


@Composable
fun NowPlaying(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val nowPlayViewModel = hiltViewModel<NowPlayingViewModel>()
    MovieItemList(
        navController = navController,
        movies = nowPlayViewModel.nowPlayingMovies,
        genres = genres,
        selectedName = nowPlayViewModel.selectedGenre.value
    ){
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}