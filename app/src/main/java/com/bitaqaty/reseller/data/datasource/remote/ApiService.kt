package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.artist.Artist
import com.bitaqaty.reseller.data.model.artist.ArtistDetail
import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    @GET("movie/now_playing")
//    suspend fun nowPlayingMovieList(
//        @Query("page") page: Int,
//        @Query("with_genres") genreId: String?,
//        @Query("api_key") api_key: String = ApiURL.API_KEY
//    ): BaseModel

    @POST(Globals.GET_TRANSACTIONS_LIST)
    suspend fun getTransactionLogList(@Body jsonObject: JsonObject):
            DataState<PairType<TransactionLogResult, Void>>


    @GET("movie/popular")
    suspend fun popularMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("movie/top_rated")
    suspend fun topRatedMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("movie/upcoming")
    suspend fun upcomingMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): MovieDetail

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int,
        @Query("page") one: Int,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("search/movie?page=1&include_adult=false")
    suspend fun search(
        @Query("query") searchKey: String, @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("genre/movie/list")
    suspend fun genreList(@Query("api_key") api_key: String = ApiURL.API_KEY)

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    )

    @GET("movie/{movieId}/credits")
    suspend fun movieCredit(
        @Path("movieId") movieId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): Artist

    @GET("person/{personId}")
    suspend fun artistDetail(
        @Path("personId") personId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): ArtistDetail
}