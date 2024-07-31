package com.bitaqaty.reseller.utilities.network


/**
 * Data state for processing api response Loading, Success and Error
 */

//data class PairType<out T : Any, out U : Any>(val first: T, val second: U)

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
//
    /**
     * Success response with body
//     */
//    data class Success<T : Any>(val body: T, val msg: String? = null) :
//        DataState<PairType<T, Nothing>>()
//
//    /**
//     * Failure response with body
//     */
//    data class ApiError<U : Any>(val body: U, val code: Int)
//        : DataState<PairType<Nothing, U>>()
//
//    /**
//     * Network error
//     */
//    data class NetworkError(val error: Exception) : DataState<PairType<Nothing, Nothing>>()
//
//    /**
//     * For example, json parsing error
//     */
//    data class UnknownError(val error: Throwable?, val code: Int = -1) :
//        DataState<PairType<Nothing, Nothing>>()
//
//    /**
//     * For example, json parsing error
//     */
//    data class EmptyResponse(val code: Int = -1) : DataState<PairType<Nothing, Nothing>>()

}