package com.bitaqaty.reseller.data.localStorage

import androidx.room.*

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


@Dao
interface ProductDao {
    @Query("select * from productmodel ")
    fun getAllProducts(): Maybe<List<ProductModel>>

    @Query("delete from productmodel")
    fun emptyBag(): Completable


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductToBag(vararg productModel: ProductModel): Completable



}