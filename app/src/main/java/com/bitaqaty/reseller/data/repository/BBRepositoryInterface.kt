package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

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