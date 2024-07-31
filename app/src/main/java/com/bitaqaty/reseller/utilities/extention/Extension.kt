package com.bitaqaty.reseller.utilities.extention

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified R> String.fromJson(): R {
    return Gson().fromJson(this, R::class.java)
}

inline fun <reified R> R.toJson(): String {
    return Gson().toJson(this, R::class.java)
}

inline fun <reified T> T.setList(key: String?, list: List<T>?) {
    val gson = Gson()
    val json = gson.toJson(list)
    //set(key, json)
}

fun getMap(json: Any?): Map<String, Any>?{
    return Gson().fromJson(Gson().toJson(json), object : TypeToken<Map<String, Any>>() {}.type)
}
