package com.bitaqaty.reseller.utilities

import android.graphics.Bitmap
import android.util.Log
import com.bitaqaty.reseller.utilities.bluetooth.BluetoothUtil
import com.bitaqaty.reseller.utilities.bluetooth.ESCUtil

 fun printTransaction(transaction: Bitmap) {
    if (!BluetoothUtil.isBlueToothPrinter) {
        SunmiPrintHelper.getInstance().printBitmap(transaction)
        SunmiPrintHelper.getInstance().feedPaper()

    } else {
        BluetoothUtil.sendData(ESCUtil.printBitmap(transaction))
        BluetoothUtil.sendData(ESCUtil.nextLine(3))
    }
}
