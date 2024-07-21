package com.bitaqaty.reseller.utilities

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

fun getBitmapFromView(view: View?): Bitmap {
    view!!.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth, view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.draw(canvas)
    return bitmap
}