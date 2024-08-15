package com.bitaqaty.reseller.utilities

import android.content.Context
import android.graphics.Bitmap
import android.os.RemoteException
import android.widget.Toast
import com.sunmi.peripheral.printer.*
import com.bitaqaty.reseller.utilities.bluetooth.ESCUtil
open class SunmiPrintHelper {
    companion object {
        var NoSunmiPrinter = 0x00000000
        var CheckSunmiPrinter = 0x00000001
        var FoundSunmiPrinter = 0x00000002
        var LostSunmiPrinter = 0x00000003
        private val helper: SunmiPrintHelper = SunmiPrintHelper()

        fun getInstance(): SunmiPrintHelper {
            return helper
        }
    }

    /**
     * sunmiPrinter means checking the printer connection status
     */
    var sunmiPrinter = CheckSunmiPrinter

    /**
     * SunmiPrinterService for API
     */
    private var sunmiPrinterService: SunmiPrinterService? = null


    private fun SunmiPrintHelper() {}


    private val innerPrinterCallback: InnerPrinterCallback = object : InnerPrinterCallback() {
        override fun onConnected(service: SunmiPrinterService) {
            sunmiPrinterService = service
            checkSunmiPrinterService(service)
        }

        override fun onDisconnected() {
            sunmiPrinterService = null
            sunmiPrinter = LostSunmiPrinter
        }
    }

    /**
     * init sunmi print service
     */
    fun initSunmiPrinterService(context: Context?) {
        try {
            val ret = InnerPrinterManager.getInstance().bindService(
                context,
                innerPrinterCallback
            )
            if (!ret) {
                sunmiPrinter = NoSunmiPrinter
            }
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }

    /**
     * deInit sunmi print service
     */
    fun deInitSunmiPrinterService(context: Context?) {
        try {
            if (sunmiPrinterService != null) {
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback)
                sunmiPrinterService = null
                sunmiPrinter = LostSunmiPrinter
            }
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }

    /**
     * Check the printer connection,
     * like some devices do not have a printer but need to be connected to the cash drawer through a print service
     */
    private fun checkSunmiPrinterService(service: SunmiPrinterService) {
        var ret = false
        try {
            ret = InnerPrinterManager.getInstance().hasPrinter(service)
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
        sunmiPrinter = if (ret) FoundSunmiPrinter else NoSunmiPrinter
    }

    /**
     * Some conditions can cause interface calls to fail
     * For example: the version is too low、device does not support
     * You can see [ExceptionConst]
     * So you have to handle these exceptions
     */
    private fun handleRemoteException(e: RemoteException) {
        //TODO process when get one exception
    }

    /**
     * send esc cmd
     */
    fun sendRawData(data: ByteArray?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.sendRAWData(data, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Printer cuts paper and throws exception on machines without a cutter
     */
    fun cutpaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.cutPaper(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Initialize the printer
     * All style settings will be restored to default
     */
    fun initPrinter() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printerInit(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    fun exitPrinterPrinter() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService?.exitPrinterBuffer(true)
            sunmiPrinterService?.commitPrinterBuffer()
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }


    /**
     * paper feed three lines
     * Not disabled when line spacing is set to 0
     */
    fun print3Line() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.lineWrap(3, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Get printer serial number
     */
    fun getPrinterSerialNo(): String? {
        return if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerSerialNo
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }
    }

    /**
     * Get device model
     */
    fun getDeviceModel(): String? {
        return if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerModal
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }
    }

    /**
     * Get firmware version
     */
    fun getPrinterVersion(): String? {
        return if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerVersion
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }
    }

    /**
     * Get paper specifications
     */
    fun getPrinterPaper(): String? {
        return if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            if (sunmiPrinterService!!.printerPaper == 1) "58mm" else "80mm"
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }
    }

    /**
     * Get paper specifications
     */
    fun getPrinterHead(callbcak: InnerResultCallback?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.getPrinterFactory(callbcak)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Get printing distance since boot
     * Get printing distance through interface callback since 1.0.8(printerlibrary)
     */
    fun getPrinterDistance(callback: InnerResultCallback?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.getPrintedLength(callback)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Set printer alignment
     */
    fun setAlign(align: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.setAlignment(align, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Due to the distance between the paper hatch and the print head,
     * the paper needs to be fed out automatically
     * But if the Api does not support it, it will be replaced by printing three lines
     */
    fun feedPaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.autoOutPaper(null)
        } catch (e: RemoteException) {
            print3Line()
        }
    }

    fun checkPrinterPaper(): Int {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return 0
        }
        return try {
            sunmiPrinterService!!.updatePrinterState()
        } catch (e: RemoteException) {
            print3Line()
            0
        }
    }

    fun printBitmap(bitmap: Bitmap?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printBitmap(bitmap, object : InnerResultCallback() {
                override fun onRunResult(isSuccess: Boolean) {
                }

                override fun onReturnString(result: String?) {
                }

                override fun onRaiseException(code: Int, msg: String?) {
                }

                override fun onPrintResult(code: Int, msg: String?) {
                }

            })
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Used to report the real-time query status of the printer, which can be used before each
     * printing
     */
    fun showPrinterStatus(context: Context?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        var result = "Interface is too low to implement interface"
        try {
            val res = sunmiPrinterService!!.updatePrinterState()
            when (res) {
                1 -> result = "printer is running"
                2 -> result = "printer found but still initializing"
                3 -> result = "printer hardware interface is abnormal and needs to be reprinted"
                4 -> result = "printer is out of paper"
                5 -> result = "printer is overheating"
                6 -> result = "printer's cover is not closed"
                7 -> result = "printer's cutter is abnormal"
                8 -> result = "printer's cutter is normal"
                9 -> result = "not found black mark paper"
                505 -> result = "printer does not exist"
                else -> {
                }
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        Toast.makeText(context, result, Toast.LENGTH_LONG).show()
    }
    open fun printText(
        content: String?, size: Float, isBold: Boolean, isUnderLine: Boolean,
        typeface: String?
    ) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            try {
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_BOLD,
                    if (isBold) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
            } catch (e: RemoteException) {
                if (isBold) {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOn(), null)
                } else {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOff(), null)
                }
            }
            try {
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_UNDERLINE,
                    if (isUnderLine) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
            } catch (e: RemoteException) {
                if (isUnderLine) {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null)
                } else {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.underlineOff(), null)
                }
            }
            sunmiPrinterService!!.printTextWithFont(content, typeface, size, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }
}