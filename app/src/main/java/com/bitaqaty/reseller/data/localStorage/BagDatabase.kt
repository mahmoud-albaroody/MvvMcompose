package com.bitaqaty.reseller.data.localStorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.converter.gson.GsonConverterFactory


@Database(entities = [ProductModel::class], version = 5)
abstract class BagDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}

private lateinit var INSTANCE: BagDatabase

fun getDatabase(context: Context): BagDatabase {
    synchronized(BagDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BagDatabase::class.java,
                "product.db"
            ).fallbackToDestructiveMigration()
                // .addTypeConverter(GsonConverterFactory.create())
                .build()

        }
    }
    return INSTANCE
}