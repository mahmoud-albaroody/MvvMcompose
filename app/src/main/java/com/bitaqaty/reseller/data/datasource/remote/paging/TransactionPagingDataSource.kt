package com.bitaqaty.reseller.data.datasource.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.data.model.TransactionRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TransactionPagingDataSource @Inject constructor(private val apiService: ApiService,
                                                private val transactionRequestBody: TransactionRequestBody
) : PagingSource<Int, TransactionLog>() {

    override fun getRefreshKey(state: PagingState<Int, TransactionLog>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionLog> {
        return try {
            val nextPage = params.key ?: 1
            transactionRequestBody.pageNumber  = params.key ?: 1
            val movieList = apiService.getTransactionLogList(transactionRequestBody)
            LoadResult.Page(
                data = movieList.body()?.transactionLogList!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =  if (movieList.body()?.transactionLogList!!.isNotEmpty())
                    transactionRequestBody.pageNumber + 1
                else  null
            )
        } catch (exception: IOException) {
           // Timber.e("exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            //Timber.e("httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }
}