package com.bitaqaty.reseller.utilities.bluetooth

import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream
import java.util.Locale

//常用指令封装
object ESCUtil {
    // UNICODE 0x23 = #
    val UNICODE_TEXT = byteArrayOf(
        0x23, 0x23, 0x23,
        0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
        0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
        0x23, 0x23, 0x23
    )
    private const val hexStr = "0123456789ABCDEF"
    private val binaryArray = arrayOf(
        "0000", "0001", "0010", "0011",
        "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
        "1100", "1101", "1110", "1111"
    )
    const val ESC: Byte = 0x1B // Escape
    const val FS: Byte = 0x1C // Text delimiter
    const val GS: Byte = 0x1D // Group separator
    const val DLE: Byte = 0x10 // data link escape
    const val EOT: Byte = 0x04 // End of transmission
    const val ENQ: Byte = 0x05 // Enquiry character
    const val SP: Byte = 0x20 // Spaces
    const val HT: Byte = 0x09 // Horizontal list
    const val LF: Byte = 0x0A //Print and wrap (horizontal orientation)
    const val CR: Byte = 0x0D // Home key
    const val FF: Byte =
        0x0C // Carriage control (print and return to the standard mode (in page mode))
    const val CAN: Byte = 0x18 // Canceled (cancel print data in page mode)

    //初始化打印机
    fun init_printer(): ByteArray {
        val result = ByteArray(2)
        result[0] = ESC
        result[1] = 0x40
        return result
    }

    //打印浓度指令
    fun setPrinterDarkness(value: Int): ByteArray {
        val result = ByteArray(9)
        result[0] = GS
        result[1] = 40
        result[2] = 69
        result[3] = 4
        result[4] = 0
        result[5] = 5
        result[6] = 5
        result[7] = (value shr 8).toByte()
        result[8] = value.toByte()
        return result
    }

    /**
     * 打印单个二维码 sunmi自定义指令
     * @param code:			二维码数据
     * @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
     * @param errorlevel:	二维码纠错等级(0 至 3)
     * 0 -- 纠错级别L ( 7%)
     * 1 -- 纠错级别M (15%)
     * 2 -- 纠错级别Q (25%)
     * 3 -- 纠错级别H (30%)
     */
    fun getPrintQRCode(code: String, modulesize: Int, errorlevel: Int): ByteArray {
        val buffer = ByteArrayOutputStream()
        try {
            buffer.write(setQRCodeSize(modulesize))
            buffer.write(setQRCodeErrorLevel(errorlevel))
            buffer.write(getQCodeBytes(code))
            buffer.write(getBytesForPrintQRCode(true))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }

    /**
     * 横向两个二维码 sunmi自定义指令
     * @param code1:			二维码数据
     * @param code2:			二维码数据
     * @param modulesize:	二维码块大小(单位:点, 取值 1 至 16 )
     * @param errorlevel:	二维码纠错等级(0 至 3)
     * 0 -- 纠错级别L ( 7%)
     * 1 -- 纠错级别M (15%)
     * 2 -- 纠错级别Q (25%)
     * 3 -- 纠错级别H (30%)
     */
    fun getPrintDoubleQRCode(
        code1: String,
        code2: String,
        modulesize: Int,
        errorlevel: Int
    ): ByteArray {
        val buffer = ByteArrayOutputStream()
        try {
            buffer.write(setQRCodeSize(modulesize))
            buffer.write(setQRCodeErrorLevel(errorlevel))
            buffer.write(getQCodeBytes(code1))
            buffer.write(getBytesForPrintQRCode(false))
            buffer.write(getQCodeBytes(code2))

            //加入横向间隔
            buffer.write(byteArrayOf(0x1B, 0x5C, 0x18, 0x00))
            buffer.write(getBytesForPrintQRCode(true))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }

    /**
     * 光栅打印二维码
     */
    fun getPrintQRCode2(data: String?, size: Int): ByteArray {
        val bytes1 = ByteArray(4)
        bytes1[0] = GS
        bytes1[1] = 0x76
        bytes1[2] = 0x30
        bytes1[3] = 0x00
        val bytes2 = BytesUtil.getZXingQRCode(data, size)
        return BytesUtil.byteMerger(bytes1, bytes2)
    }

    /**
     * 打印一维条形码
     */
    fun getPrintBarCode(
        data: String,
        symbology: Int,
        height: Int,
        width: Int,
        textposition: Int
    ): ByteArray {
        var height = height
        var width = width
        var textposition = textposition
        if (symbology < 0 || symbology > 10) {
            return byteArrayOf(LF)
        }
        if (width < 2 || width > 6) {
            width = 2
        }
        if (textposition < 0 || textposition > 3) {
            textposition = 0
        }
        if (height < 1 || height > 255) {
            height = 162
        }
        val buffer = ByteArrayOutputStream()
        try {
            buffer.write(
                byteArrayOf(
                    0x1D, 0x66, 0x01, 0x1D, 0x48, textposition.toByte(),
                    0x1D, 0x77, width.toByte(), 0x1D, 0x68, height.toByte(), 0x0A
                )
            )
            val barcode: ByteArray
            barcode = if (symbology == 10) {
                BytesUtil.getBytesFromDecString(data)
            } else {
                data.toByteArray(charset("GB18030"))
            }
            if (symbology > 7) {
                buffer.write(
                    byteArrayOf(
                        0x1D,
                        0x6B,
                        0x49,
                        (barcode.size + 2).toByte(),
                        0x7B,
                        (0x41 + symbology - 8).toByte()
                    )
                )
            } else {
                buffer.write(
                    byteArrayOf(
                        0x1D,
                        0x6B,
                        (symbology + 0x41).toByte(),
                        barcode.size.toByte()
                    )
                )
            }
            buffer.write(barcode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }

    //光栅位图打印
    fun printBitmap(bitmap: Bitmap?): ByteArray {
        val bytes1 = ByteArray(4)
        bytes1[0] = GS
        bytes1[1] = 0x76
        bytes1[2] = 0x30
        bytes1[3] = 0x00
        val bytes2 = BytesUtil.getBytesFromBitMap(bitmap)
        return BytesUtil.byteMerger(bytes1, bytes2)
    }

    //光栅位图打印 设置mode
    fun printBitmap(bitmap: Bitmap?, mode: Int): ByteArray {
        val bytes1 = ByteArray(4)
        bytes1[0] = GS
        bytes1[1] = 0x76
        bytes1[2] = 0x30
        bytes1[3] = mode.toByte()
        val bytes2 = BytesUtil.getBytesFromBitMap(bitmap)
        return BytesUtil.byteMerger(bytes1, bytes2)
    }

    //光栅位图打印
    fun printBitmap(bytes: ByteArray?): ByteArray {
        val bytes1 = ByteArray(4)
        bytes1[0] = GS
        bytes1[1] = 0x76
        bytes1[2] = 0x30
        bytes1[3] = 0x00
        return BytesUtil.byteMerger(bytes1, bytes)
    }

    /*
     *	选择位图指令 设置mode
     *	需要设置1B 33 00将行间距设为0
     */
    fun selectBitmap(bitmap: Bitmap?, mode: Int): ByteArray {
        return BytesUtil.byteMerger(
            BytesUtil.byteMerger(
                byteArrayOf(0x1B, 0x33, 0x00),
                BytesUtil.getBytesFromBitMap(bitmap, mode)
            ), byteArrayOf(0x0A, 0x1B, 0x32)
        )
    }

    /**
     * 跳指定行数
     */
    fun nextLine(lineNum: Int): ByteArray {
        val result = ByteArray(lineNum)
        for (i in 0 until lineNum) {
            result[i] = LF
        }
        return result
    }

    // ------------------------style set-----------------------------
    //设置默认行间距
    fun setDefaultLineSpace(): ByteArray {
        return byteArrayOf(0x1B, 0x32)
    }

    //设置行间距
    fun setLineSpace(height: Int): ByteArray {
        return byteArrayOf(0x1B, 0x33, height.toByte())
    }

    // ------------------------underline-----------------------------
    //设置下划线1点
    fun underlineWithOneDotWidthOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 1
        return result
    }

    //设置下划线2点
    fun underlineWithTwoDotWidthOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 2
        return result
    }

    //取消下划线
    fun underlineOff(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 0
        return result
    }
    // ------------------------bold-----------------------------
    /**
     * 字体加粗
     */
    fun boldOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 69
        result[2] = 0xF
        return result
    }

    /**
     * 取消字体加粗
     */
    fun boldOff(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 69
        result[2] = 0
        return result
    }

    // ------------------------character-----------------------------
    /*
     *单字节模式开启
     */
    fun singleByte(): ByteArray {
        val result = ByteArray(2)
        result[0] = FS
        result[1] = 0x2E
        return result
    }

    /*
     *单字节模式关闭
     */
    fun singleByteOff(): ByteArray {
        val result = ByteArray(2)
        result[0] = FS
        result[1] = 0x26
        return result
    }

    /**
     * 设置单字节字符集
     */
    fun setCodeSystemSingle(charset: Byte): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 0x74
        result[2] = charset
        return result
    }

    /**
     * 设置多字节字符集
     */
    fun setCodeSystem(charset: Byte): ByteArray {
        val result = ByteArray(3)
        result[0] = FS
        result[1] = 0x43
        result[2] = charset
        return result
    }
    // ------------------------Align-----------------------------
    /**
     * 居左
     */
    fun alignLeft(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 0
        return result
    }

    /**
     * 居中对齐
     */
    fun alignCenter(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 1
        return result
    }

    /**
     * 居右
     */
    fun alignRight(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 2
        return result
    }

    //切刀
    fun cutter(): ByteArray {
        return byteArrayOf(0x1d, 0x56, 0x01)
    }

    //走纸到黑标
    fun gogogo(): ByteArray {
        return byteArrayOf(0x1C, 0x28, 0x4C, 0x02, 0x00, 0x42, 0x31)
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////          private                /////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    private fun setQRCodeSize(modulesize: Int): ByteArray {
        //二维码块大小设置指令
        val dtmp = ByteArray(8)
        dtmp[0] = GS
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x43
        dtmp[7] = modulesize.toByte()
        return dtmp
    }

    private fun setQRCodeErrorLevel(errorlevel: Int): ByteArray {
        //二维码纠错等级设置指令
        val dtmp = ByteArray(8)
        dtmp[0] = GS
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x45
        dtmp[7] = (48 + errorlevel).toByte()
        return dtmp
    }

    private fun getBytesForPrintQRCode(single: Boolean): ByteArray {
        //打印已存入数据的二维码
        val dtmp: ByteArray
        if (single) {        //同一行只打印一个QRCode， 后面加换行
            dtmp = ByteArray(9)
            dtmp[8] = 0x0A
        } else {
            dtmp = ByteArray(8)
        }
        dtmp[0] = 0x1D
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x51
        dtmp[7] = 0x30
        return dtmp
    }

    private fun getQCodeBytes(code: String): ByteArray {
        //二维码存入指令
        val buffer = ByteArrayOutputStream()
        try {
            val d = code.toByteArray(charset("GB18030"))
            var len = d.size + 3
            if (len > 7092) len = 7092
            buffer.write(0x1D.toByte().toInt())
            buffer.write(0x28.toByte().toInt())
            buffer.write(0x6B.toByte().toInt())
            buffer.write(len.toByte().toInt())
            buffer.write((len shr 8).toByte().toInt())
            buffer.write(0x31.toByte().toInt())
            buffer.write(0x50.toByte().toInt())
            buffer.write(0x30.toByte().toInt())
            var i = 0
            while (i < d.size && i < len) {
                buffer.write(d[i].toInt())
                i++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }

    fun decodeBitmap(bmp: Bitmap): ByteArray? {
        val bmpWidth = bmp.width
        val bmpHeight = bmp.height
        val list: MutableList<String> = ArrayList() //binaryString list
        var sb: StringBuffer
        var bitLen = bmpWidth / 8
        val zeroCount = bmpWidth % 8
        var zeroStr = ""
        if (zeroCount > 0) {
            bitLen = bmpWidth / 8 + 1
            for (i in 0 until 8 - zeroCount) {
                zeroStr = zeroStr + "0"
            }
        }
        for (i in 0 until bmpHeight) {
            sb = StringBuffer()
            for (j in 0 until bmpWidth) {
                val color = bmp.getPixel(j, i)
                val r = color shr 16 and 0xff
                val g = color shr 8 and 0xff
                val b = color and 0xff

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160) sb.append("0") else sb.append("1")
            }
            if (zeroCount > 0) {
                sb.append(zeroStr)
            }
            list.add(sb.toString())
        }
        val bmpHexList = binaryListToHexStringList(list)
        val commandHexString = "1D763000"
        var widthHexString = Integer
            .toHexString(if (bmpWidth % 8 == 0) bmpWidth / 8 else bmpWidth / 8 + 1)
        if (widthHexString.length > 2) {
            Log.e("decodeBitmap error", " width is too large")
            return null
        } else if (widthHexString.length == 1) {
            widthHexString = "0$widthHexString"
        }
        widthHexString = widthHexString + "00"
        var heightHexString = Integer.toHexString(bmpHeight)
        if (heightHexString.length > 2) {
            Log.e("decodeBitmap error", " height is too large")
            return null
        } else if (heightHexString.length == 1) {
            heightHexString = "0$heightHexString"
        }
        heightHexString = heightHexString + "00"
        val commandList: MutableList<String> = ArrayList()
        commandList.add(commandHexString + widthHexString + heightHexString)
        commandList.addAll(bmpHexList)
        return hexList2Byte(commandList)
    }

    fun binaryListToHexStringList(list: List<String>): List<String> {
        val hexList: MutableList<String> = ArrayList()
        for (binaryStr in list) {
            val sb = StringBuffer()
            var i = 0
            while (i < binaryStr.length) {
                val str = binaryStr.substring(i, i + 8)
                val hexString = myBinaryStrToHexString(str)
                sb.append(hexString)
                i += 8
            }
            hexList.add(sb.toString())
        }
        return hexList
    }

    fun myBinaryStrToHexString(binaryStr: String): String {
        var hex = ""
        val f4 = binaryStr.substring(0, 4)
        val b4 = binaryStr.substring(4, 8)
        for (i in binaryArray.indices) {
            if (f4 == binaryArray[i]) hex += hexStr.substring(i, i + 1)
        }
        for (i in binaryArray.indices) {
            if (b4 == binaryArray[i]) hex += hexStr.substring(i, i + 1)
        }
        return hex
    }

    fun hexList2Byte(list: List<String>): ByteArray {
        val commandList: MutableList<ByteArray?> =
            ArrayList()
        for (hexStr in list) {
            commandList.add(hexStringToBytes(hexStr))
        }
        return sysCopy(commandList)
    }

    fun hexStringToBytes(hexString: String?): ByteArray? {
        var hexString = hexString
        if (hexString == null || hexString == "") {
            return null
        }
        hexString = hexString.uppercase(Locale.getDefault())
        val length = hexString.length / 2
        val hexChars = hexString.toCharArray()
        val d = ByteArray(length)
        for (i in 0 until length) {
            val pos = i * 2
            d[i] =
                (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
        }
        return d
    }

    fun sysCopy(srcArrays: List<ByteArray?>): ByteArray {
        var len = 0
        for (srcArray in srcArrays) {
            len += srcArray!!.size
        }
        val destArray = ByteArray(len)
        var destLen = 0
        for (srcArray in srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray!!.size)
            destLen += srcArray.size
        }
        return destArray
    }

    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }
}