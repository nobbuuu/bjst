package com.dream.bjst.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.rxlife.coroutine.RxLifeScope
import com.tcl.base.kt.ktToastShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rxhttp.async
import rxhttp.toBitmap
import rxhttp.tryAwait
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {

    @JvmStatic
    fun bitmap2File(bm: Bitmap, path: String, fileName: String): File? {
        val dirFile = File(path)
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        val file = File(path, fileName)
        val bos = BufferedOutputStream(FileOutputStream(file))
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush()
        bos.close()
        return file
    }

    @SuppressLint("CheckResult")
    fun saveFile2Gallery(
        context: Context,
        url: List<String>,
        save: Boolean = true,
        callBack: ((Bitmap) -> Unit)? = null,
        final: (() -> Unit)? = null
    ) {
        RxLifeScope().launch {
            LogUtils.iTag("mars", ">>当前线程：" + Thread.currentThread().name)
            val iAwaits = mutableListOf<Deferred<Bitmap>>()
            repeat(url.size) {
                val image = RxHttp.get(url[it])
                    .setCacheMode(CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK).toBitmap()
                    .async(this)
                iAwaits.add(image)
            }

            iAwaits.forEach {
                it.tryAwait()?.run {
                    if (save) {
                        //卡主线程
                        RxLifeScope().launch {
                            LogUtils.iTag("mars", "当前线程：" + Thread.currentThread().name)
                            ImageUtils.save2Album(this@run, Bitmap.CompressFormat.PNG)
                        }
                    } else {
                        callBack?.invoke(this)
                    }
                }
            }
            if (save) {
                "图片已保存到本地相册".ktToastShow()
                final?.invoke()
            }
        }
    }


    @SuppressLint("CheckResult")
    suspend fun saveFile2GalleryBetter(
        context: Context,
        url: List<String>,
        save: Boolean = true,
        callBack: ((Bitmap) -> Unit)? = null,
        final: (() -> Unit)? = null,
        lifeScope: CoroutineScope
    ) {

        LogUtils.iTag("mars", ">>当前线程：" + Thread.currentThread().name)
        val iAwaits = mutableListOf<Deferred<Bitmap>>()
        repeat(url.size) {
            val image = RxHttp.get(url[it])
                .setCacheMode(CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK).toBitmap()
                .async(lifeScope)
            iAwaits.add(image)
        }

        iAwaits.forEach {
            it.tryAwait()?.run {
                if (save) {
                    LogUtils.iTag("mars", "当前线程：" + Thread.currentThread().name)
                    ImageUtils.save2Album(this@run, Bitmap.CompressFormat.PNG)
                } else {
                    callBack?.invoke(this)
                }
            }
        }
        if (save) {
            lifeScope.launch(Dispatchers.Main) {
                "图片已保存到本地相册".ktToastShow()
            }
            final?.invoke()
        }
    }


    /**在Android 10 以下进行保存图片*/
    fun saveImageToGallery(context: Context, bmp: Bitmap, path: String, fileName: String): Boolean {
        val appDir = File(path)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val file = File(appDir, "$fileName.png")
        try {
            val fos = FileOutputStream(file)
            val isSuccess = bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            val uri = Uri.fromFile(file)
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            return isSuccess
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**在Android 11 及以上进行保存图片*/
    fun saveAbove11(context: Context, image: Bitmap, fileName: String): Boolean {
        val imageTime = System.currentTimeMillis()
        val values = ContentValues()
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES
                    + File.separator + AppUtils.getAppName()
        ) //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        values.put(MediaStore.MediaColumns.DATE_ADDED, imageTime / 1000)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, imageTime / 1000)
        values.put(
            MediaStore.MediaColumns.DATE_EXPIRES,
            (imageTime + DateUtils.DAY_IN_MILLIS) / 1000
        )
        values.put(MediaStore.MediaColumns.IS_PENDING, 1)

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null) return false
        try {
            // First, write the actual data for our screenshot
            val out = resolver.openOutputStream(uri)
            if (!image.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                return false
            }
            values.clear()
            values.put(MediaStore.MediaColumns.IS_PENDING, 0)
            values.putNull(MediaStore.MediaColumns.DATE_EXPIRES)
            resolver.update(uri, values, null, null)
            return true
        } catch (e: IOException) {
            resolver.delete(uri, null)
        }
        return false
    }

}