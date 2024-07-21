package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<DataState<PairType<S, E>>> {

    override fun enqueue(callback: Callback<DataState<PairType<S, E>>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                DataState.Success(
                                    body,
                                    response.headers()["Message"]
                                )
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(DataState.EmptyResponse(code))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }

                    }
                    if (errorBody != null) {
                        DataState.ApiError(errorBody, code)
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(DataState.ApiError(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(DataState.UnknownError(null, code))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> DataState.NetworkError(throwable)
                    else -> DataState.UnknownError(throwable)
                }
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(networkResponse)
                )
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<DataState<PairType<S, E>>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout {
        return delegate.timeout()
    }

    companion object {
        private const val TAG = "NetworkResponseCall"
    }
}
