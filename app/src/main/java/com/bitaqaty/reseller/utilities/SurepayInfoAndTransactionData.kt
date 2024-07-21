package com.bitaqaty.reseller.utilities

import android.app.Activity
import android.content.Intent
import android.widget.Toast
const val key = "MGZmLTkyNjYtNmIyYjYtNWEwYi00MDdiLTkxMmW0DFrjHiR3"
fun Activity.madaConnectionResult(totalAmount:String?=null,refName:String?=null, keyword:String?=null) {
    val intent = packageManager.getLaunchIntentForPackage("com.surepay.mada")
    if(!totalAmount.isNullOrEmpty()) {
        intent?.putExtra("AMOUNT", totalAmount)
        intent?.putExtra("REFERENCE", refName)
    }
    if(!keyword.isNullOrEmpty()) {
        intent?.putExtra(keyword, true)
    }
    intent?.putExtra("LICENCE",key )
    startActivity(intent)
}

