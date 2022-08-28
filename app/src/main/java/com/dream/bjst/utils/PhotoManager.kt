package com.dream.bjst.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import com.blankj.utilcode.util.ImageUtils
import com.dream.bjst.common.Constant.CAMERA_REQUEST_CODE
import com.dream.bjst.common.Constant.CHOOSE_PHOTO_CODE
import com.dream.bjst.common.Constant.PERMISSION_CAMERA_REQUEST_CODE
import com.dream.bjst.common.Constant.PERMISSION_STORAGE_REQUEST_CODE
import com.dream.bjst.utils.FileUtils.bitmap2File
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
class PhotoManager (activity: Activity){


    //用于保存拍照图片的uri
    @kotlin.jvm.JvmField
    var mCameraUri: Uri? = null

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    @kotlin.jvm.JvmField
    var mCameraImagePath: String = ""

    // 是否是Android 10以上手机
    @kotlin.jvm.JvmField
    val isAndroidQ: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    val CROP_IMG_CODE = 0x00000015
    val context = activity

    lateinit var photoUri: Uri
    var imagePath: String? = null

    //供裁剪使用
    lateinit var oriUri: Uri

    init {
        initOriUri()
    }

    fun initOriUri() {
        val file = File(context.filesDir.path, "cropImage.jpeg")
        if (!file.exists()) {
            file.mkdirs()
        }
         /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             //7.0适配
             oriUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
         }else{
         }*/
        oriUri = Uri.fromFile(file)
    }

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    fun checkPermissionAndCamera() {
        val hasCameraPermission: Int = ContextCompat.checkSelfPermission(
            context.application,
            Manifest.permission.CAMERA
        )
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera()
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(
                context, arrayOf<String>(Manifest.permission.CAMERA),
                PERMISSION_CAMERA_REQUEST_CODE
            )
        }
    }

    /**
     * 检查存储权限。
     */
    fun checkPermissionAndChosePhoto() {
        val hasCameraPermission: Int = ContextCompat.checkSelfPermission(
            context.application,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            goPhotoAlbum()
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(
                context, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_STORAGE_REQUEST_CODE
            )
        }
    }

    fun goPhotoAlbum() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            context.startActivityForResult(this, CHOOSE_PHOTO_CODE)
        }
    }

    /**
     * 调起相机拍照
     */
    fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 判断是否有相机
        if (captureIntent.resolveActivity(context.packageManager) != null) {
            var photoFile: File? = null
            var photoUri: Uri? = null
            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri()
            } else {
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath()
                    photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
                            photoFile
                        )
                    } else {
                        Uri.fromFile(photoFile)
                    }
                }
            }
            mCameraUri = photoUri
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                context.startActivityForResult(captureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private fun createImageUri(): Uri? {
        val status: String = Environment.getExternalStorageState()
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status == Environment.MEDIA_MOUNTED) {
            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )
        } else {
            context.contentResolver.insert(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                ContentValues()
            )
        }
    }

    /**
     * 创建保存图片的文件
     */
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val imageName: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir?.let {
            if (!it.exists()) {
                it.mkdir()
            }
            val tempFile = File(storageDir, imageName)
            return if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
                null
            } else tempFile
        }
        return null
    }

    /**
     * android4.4之后，需要解析获取图片真实路径
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun handleImageAfterKitKat(data: Intent?) {
        val uri = data?.data
        //document类型的Uri
        when {
            DocumentsContract.isDocumentUri(context, uri) -> {
                //通过documentId处理
                val docId = DocumentsContract.getDocumentId(uri)
                when (uri?.authority) {
                    "com.android.externalstorage.documents" -> {
                        val type = docId.split(":")[0]
                        if ("primary".equals(type, ignoreCase = true)) {
                            imagePath = Environment.getExternalStorageDirectory()
                                .toString() + "/" + docId.split(":")[1]
                        }
                    }
                    //media类型解析
                    "com.android.providers.media.documents" -> {
                        val id = docId.split(":")[1]
                        val type = docId.split(":")[0]
                        val contentUri: Uri? = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        val selection = "_id=?"
                        val selectionArgs: Array<String> = arrayOf(id)
                        imagePath = getImagePath(contentUri, selection, selectionArgs)
                    }
                    //downloads文件解析
                    "com.android.providers.downloads.documents" -> {
                        ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), docId.toLong()
                        ).apply {
                            imagePath = getImagePath(this, null, null)
                        }
                    }
                    else -> {
                    }
                }
            }
            "content".equals(uri?.scheme, ignoreCase = true) ->
                //content类型数据不需要解析，直接传入生成即可
                imagePath = getImagePath(uri, null, null)
            "file".equals(uri?.scheme, ignoreCase = true) ->
                //file类型的uri直接获取图片路径即可
                imagePath = uri?.path
        }
    }

    /**
     * android4.4之前可直接获取图片真实uri
     */
    private fun handleImageBeforeKitKat(data: Intent?) {
        val uri = data?.data
        uri?.let {
            imagePath = getImagePath(uri, null, null)
        }
    }

    /**
     * 解析uri及selection
     * 获取图片真实路径
     */
    private fun getImagePath(uri: Uri?, selection: String?, selectionArgs: Array<String>?): String {
        var cursor: Cursor? = null
        try {
            uri?.let { uri ->
                cursor = context.contentResolver.query(uri, null, selection, selectionArgs, null)
                cursor?.let {
                    if (cursor != null && it.moveToFirst()) {
                        return it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                    }
                }
            }
        } finally {
            cursor?.close()
        }
        return ""
    }

    /**
     * 获取相册的图片转为bitmap
     */
    fun getBitmapByAlbum(data: Intent?): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            handleImageAfterKitKat(data)
        } else {
            handleImageBeforeKitKat(data)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0适配
            oriUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", File(imagePath))
        }
        return MediaStore.Images.Media.getBitmap(context.contentResolver, oriUri)
    }

    // 通过uri加载图片
    fun getBitmapFromUri(uri: Uri?): Bitmap? {
        uri?.let {
            try {
                val parcelFileDescriptor: ParcelFileDescriptor? = context.contentResolver.openFileDescriptor(uri, "r")
                parcelFileDescriptor?.let {
                    val fileDescriptor: FileDescriptor = it.fileDescriptor
                    val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    it.close()
                    return image
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun getBitmapWithCamara():Bitmap?{
        return if (isAndroidQ) {
            // Android 10 使用图片uri加载
            getBitmapFromUri(mCameraUri)
        } else {
            // 使用图片路径加载
            rotateBitmap(BitmapFactory.decodeFile(mCameraImagePath))
        }
    }

    fun rotateBitmap(bitmap: Bitmap): Bitmap? {
        val bitmapC = ImageUtils.compressBySampleSize(bitmap, 2, true)
        val matrix = Matrix()
        matrix.setRotate(90f)
        return Bitmap.createBitmap(bitmapC, 0, 0, bitmapC.width, bitmapC.height, matrix, false)
    }


}