package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface BBRepositoryInterface {
    suspend fun getTransactionLogList(jsonObject: JsonObject): Flow<DataState<TransactionLogResult>>
    suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>>
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