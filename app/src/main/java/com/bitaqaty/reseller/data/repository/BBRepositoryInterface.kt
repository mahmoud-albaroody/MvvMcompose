package com.bitaqaty.reseller.data.repository

import com.bitaqaty.reseller.data.model.Category
import com.bitaqaty.reseller.data.model.ChildMerchantRequest
import com.bitaqaty.reseller.data.model.Merchant
import com.bitaqaty.reseller.data.model.PersonalBankData
import com.bitaqaty.reseller.data.model.ProductListRequest
import com.bitaqaty.reseller.data.model.ProductListResponse
import com.bitaqaty.reseller.data.model.PurchaseRequest
import com.bitaqaty.reseller.data.model.PurchaseResponse
import com.bitaqaty.reseller.data.model.SettlementRequestDataRequest
import com.bitaqaty.reseller.data.model.SettlementRequestResult
import com.bitaqaty.reseller.data.model.SystemSettings
import com.bitaqaty.reseller.data.model.TopChildMerchant
import com.bitaqaty.reseller.data.model.TopMerchants
import com.bitaqaty.reseller.data.model.TransactionLogResult
import com.bitaqaty.reseller.data.model.User
import com.bitaqaty.reseller.utilities.network.DataState
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface BBRepositoryInterface {
    suspend fun getTransactionLogList(jsonObject: JsonObject): Flow<DataState<TransactionLogResult>>
    suspend fun getCategoryList(): Flow<DataState<ArrayList<Category>>>
    suspend fun getTopMerchants(): Flow<DataState<TopMerchants>>
    suspend fun getChildMerchants(childMerchantRequest: ChildMerchantRequest): Flow<DataState<TopChildMerchant>>
    suspend fun getMerchants(categoryId: Int): Flow<DataState<ArrayList<Merchant>>>
    suspend fun getProducts(productsInfo: ProductListRequest): Flow<DataState<ProductListResponse>>
    suspend fun editCategory(currentCategoryId: Int, newCategoryId: Int): Flow<DataState<Unit>>
    suspend fun getSystemSettings(): Flow<DataState<ArrayList<SystemSettings>>>
    suspend fun getSettlementRequestData(): Flow<DataState<PersonalBankData>>
    suspend fun createSettlementRequest(settlementRequest: SettlementRequestDataRequest): Flow<DataState<SettlementRequestResult>>
    suspend fun getProfile(): Flow<DataState<User>>
    suspend fun purchaseOrder(products: PurchaseRequest): Flow<DataState<PurchaseResponse>>
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