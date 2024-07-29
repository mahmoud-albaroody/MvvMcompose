package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import javax.inject.Inject

//
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import com.bitaqaty.reseller.data.datasource.remote.paging.GenrePagingDataSource
//import com.bitaqaty.reseller.data.datasource.remote.paging.NowPlayingPagingDataSource
//import com.bitaqaty.reseller.data.datasource.remote.paging.PopularPagingDataSource
//import com.bitaqaty.reseller.data.datasource.remote.paging.UpcomingPagingDataSource
//import com.bitaqaty.reseller.data.datasource.remote.ApiService
//import com.bitaqaty.reseller.data.datasource.remote.paging.TopRatedPagingDataSource
//import com.bitaqaty.reseller.data.model.artist.Artist
//import com.bitaqaty.reseller.data.model.artist.ArtistDetail
//import com.bitaqaty.reseller.data.model.moviedetail.MovieDetail
//import com.bitaqaty.reseller.utilities.network.DataState
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import javax.inject.Inject
//
class BBRepository @Inject constructor(
    private val apiService: ApiService
) : BBRepositoryInterface {
    override suspend fun getTransactionLogList(jsonObject: JsonObject):
            Flow<DataState<TransactionLogResult>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.getTransactionLogList(jsonObject)
                emit(searchResult)
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    override suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>> =
        flow {
            emit(DataState.Loading)
            try {
                val categoryList = apiService.getCategoryList()
                emit(DataState.Success(categoryList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    override suspend fun getTopMerchants(): Flow<DataState<TopMerchants>> =
        flow {
            emit(DataState.Loading)
            try {
                val topMerchants = apiService.getTopMerchants()
                emit(DataState.Success(topMerchants))
            }catch (e: Exception){
                emit(DataState.Error(e))
            }
        }

    override suspend fun getMerchants(categoryId: Int): Flow<DataState<ArrayList<Merchant>>> =
        flow {
            emit(DataState.Loading)
            try {
                val merchants = apiService.getMerchants(categoryId)
                emit(DataState.Success(merchants))
            }catch (e: Exception){
                emit(DataState.Error(e))
            }
        }

    override suspend fun getProducts(productsInfo: ProductListRequest): Flow<DataState<ProductListResponse>> =
        flow {
            emit(DataState.Loading)
            try {
                val products = apiService.getProductList(productsInfo)
                emit(DataState.Success(products))
            }catch (e: Exception){
                emit(DataState.Error(e))
            }
        }

}
//    override suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
//        emit(DataState.Loading)
//        try {
//            val searchResult = apiService.movieDetail(movieId)
//            emit(DataState.Success(searchResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>> =
//        flow {
//            emit(DataState.Loading)
//            try {
//                val searchResult = apiService.recommendedMovie(movieId, page)
//                emit(DataState.Success(searchResult))
//
//            } catch (e: Exception) {
//                emit(DataState.Error(e))
//            }
//        }
//
//
//    override suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
//        emit(DataState.Loading)
//        try {
//            val searchResult = apiService.search(searchKey)
//            emit(DataState.Success(searchResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun genreList(): Flow<DataState<Genres>> = flow {
//        emit(DataState.Loading)
//        try {
//            val genreResult = apiService.genreList()
//            emit(DataState.Success(genreResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
//        emit(DataState.Loading)
//        try {
//            val artistResult = apiService.movieCredit(movieId)
//            emit(DataState.Success(artistResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> = flow {
//        emit(DataState.Loading)
//        try {
//            val artistDetailResult = apiService.artistDetail(personId)
//            emit(DataState.Success(artistDetailResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { NowPlayingPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun popularPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { PopularPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun topRatedPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { TopRatedPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun upcomingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { UpcomingPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { GenrePagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//}