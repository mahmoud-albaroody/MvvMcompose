package com.bitaqaty.reseller.utilities

import CTOS.CtPrint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bitaqaty.reseller.data.model.TransactionLog
import com.bitaqaty.reseller.ui.component.ReceiptComponent
import com.bitaqaty.reseller.ui.component.ReceiptVatComponent
import com.bitaqaty.reseller.ui.component.ReceiptWithoutVatComponent
import com.bitaqaty.reseller.utilities.bluetooth.BluetoothUtil
import com.bitaqaty.reseller.utilities.bluetooth.ESCUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun printTransaction(transaction: Bitmap) {
    if (!BluetoothUtil.isBlueToothPrinter) {
        SunmiPrintHelper.getInstance().printBitmap(transaction)
        SunmiPrintHelper.getInstance().feedPaper()
        SunmiPrintHelper.getInstance().exitPrinterPrinter()
    } else {
        BluetoothUtil.sendData(ESCUtil.printBitmap(transaction))
        BluetoothUtil.sendData(ESCUtil.nextLine(3))
    }
}

fun printReceipt(transactionLog: TransactionLog, context: Context, isPrintVat: Boolean) {
    val view: FrameLayout = if (isPrintVat) {
        ReceiptVatComponent(context)
    } else {
        ReceiptComponent(context)
    }
    view.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    if (isPrintVat) {
        (view as ReceiptVatComponent).setTransLogReceipt(transactionLog)
    } else {
        (view as ReceiptComponent).setTransLogReceipt(transactionLog)
    }
    view.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
    CoroutineScope(Dispatchers.IO).launch {
        Utils.view2Bitmap(view)?.let { it1 ->
            if (Utils.isMadaApp()) {
                doPrinting(view, CtPrint())
            } else {
                printTransaction(it1)
            }
        }
    }
}