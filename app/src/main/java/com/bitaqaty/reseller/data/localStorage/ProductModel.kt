package com.bitaqaty.reseller.data.localStorage

import androidx.room.*

import java.io.Serializable


@Entity
data class ProductModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "refNumber")
    var refNumber: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "isCart")
    var isCart: Boolean,


    @ColumnInfo(name = "paymentType")
    var paymentType: String,

    ) : Serializable
