package com.bitaqaty.reseller.data.datasource.remote

import com.bitaqaty.reseller.utilities.network.DataState
import com.bitaqaty.reseller.utilities.network.PairType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<S, Call<DataState<PairType<S, E>>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<DataState<PairType<S, E>>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}
