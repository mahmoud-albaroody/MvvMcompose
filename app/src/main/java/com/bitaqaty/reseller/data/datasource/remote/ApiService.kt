package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.SettlementRequestDataRequest
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.data.model.artist.Artist
import com.bitaqaty.reseller.data.model.artist.ArtistDetail
import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
import com.bitaqaty.reseller.utilities.Globals
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST(ApiURL.CATEGORIES)
    suspend fun getCategoryList(): ArrayList<Category>
    @POST(ApiURL.TOP_MERCHANTS)
    suspend fun getTopMerchants(): TopMerchants
    @POST(ApiURL.MERCHANTS)
    suspend fun getMerchants(@Path("category_id") categoryId: Int): ArrayList<Merchant>
    @POST(ApiURL.PRODUCT_LIST)
    suspend fun getProductList(@Body productsInfo: ProductListRequest): ProductListResponse
    @POST(ApiURL.SYSTEM_SETTING)
    suspend fun getSystemSetting(): ArrayList<SystemSettings>
    @POST(ApiURL.SETTLEMENT_REQUEST_DATA)
    suspend fun getSettlementRequestData(): PersonalBankData
    @POST(ApiURL.CREATE_SETTLEMENT_REQUEST)
    suspend fun createSettlementRequest(@Body settlementRequest: SettlementRequestDataRequest): SettlementRequestResult
    @POST(ApiURL.PROFILE)
    suspend fun getProfile(): User

    @POST(Globals.GET_TRANSACTIONS_LIST)
    suspend fun getTransactionLogList(@Body jsonObject: JsonObject):
            DataState<TransactionLogResult>


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