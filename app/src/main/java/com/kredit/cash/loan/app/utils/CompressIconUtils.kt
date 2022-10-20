package com.kredit.cash.loan.app.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.tcl.base.kt.bigDecimalDivide
import com.tcl.base.kt.bigDecimalPrice
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

/**
 * 创建日期：2022-10-20 on 23:48
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
object CompressIconUtils {
    /**
     * 将图片转成byte数组
     * @param bitmap 图片
     * @return 图片的字节数组
     */
    fun bitmap2Byte(bitmap: Bitmap): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun compress100kb(bitmap: Bitmap?, kb: Int): Bitmap? {
        bitmap?.let {
            val data = bitmap2Byte(bitmap)
            val total = data.toString().bigDecimalDivide("1024")
            val quality = "100".bigDecimalPrice(kb.toString()).bigDecimalDivide(total).toInt()
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byte = outputStream.toByteArray()
            return BitmapFactory.decodeByteArray(byte, 0, byte.size)
        }
        return null
    }

    /**
     * 将bitmap转化为string
     */


    fun convertIconToString(bitmap: Bitmap): String {
        var baos: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        var appicon = baos.toByteArray()// 转为byte数组
        var img = Base64.encodeToString(appicon, Base64.DEFAULT)
        return img;
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    fun convertStringToIcon(st: String?): Bitmap? {
        // OutputStream out;
        var bitmap: Bitmap? = null
        return try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            val bitmapArray: ByteArray
            bitmapArray = Base64.decode(st, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(
                bitmapArray, 0,
                bitmapArray.size
            )
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            bitmap
        } catch (e: java.lang.Exception) {
            null
        }
    }

    /**
     * 将图片转成byte数组
     *
     * @param imageByte 图片
     * @return Base64 String
     */
    fun byte2Base64(imageByte: ByteArray?): String? {
        return if (null == imageByte) null else Base64.encodeToString(imageByte, Base64.NO_WRAP)
    }

    /**
     * 加压图片数据
     */
    fun compress(str: String?): String? {
        if (str.isNullOrEmpty()) {
            return str
        }
        val o = ByteArrayOutputStream()
        val g = GZIPOutputStream(o)
        g.write(str.toByteArray(charset("utf-8")))
        g.close()
        return o.toString("ISO-8859-1")
    }


}