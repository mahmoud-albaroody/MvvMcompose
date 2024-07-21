package com.bitaqaty.reseller.data.repository

import androidx.paging.PagingData
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.artist.Artist
import com.bitaqaty.reseller.data.model.artist.ArtistDetail
import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject

interface BBRepositoryInterface {
    suspend fun getTransactionLogList(jsonObject: JsonObject): Flow<DataState<PairType<TransactionLogResult, Void>>>
//    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>>
//    suspend fun search(searchKey: String): Flow<DataState<BaseModel>>
//    suspend fun genreList(): Flow<DataState<Genres>>
//    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>>
//    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>
//    fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
//    fun popularPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
//    fun topRatedPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
//    fun upcomingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
//    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>
//}
}