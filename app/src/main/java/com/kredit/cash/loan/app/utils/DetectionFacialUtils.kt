package com.kredit.cash.loan.app.utils

import android.app.Activity
import android.Manifest
import android.content.pm.PackageManager
import android.widget.ListView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


/**
 * 创建日期：2022-08-31 on 0:32
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class DetectionFacialUtils {
    private val PERMISSION_REQUEST_CAMERA = 0
    private val PERMISSION_REQUEST_SD_WRITE = 1
    private val TAG = "Util"

    fun requestCameraPermission(context: Activity) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            // Permission has not been granted and must be
            // requested.

            // Request the permission. The result will be received
            // in onRequestPermissionResult()
            ActivityCompat.requestPermissions(
                context, arrayOf<String>(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CAMERA
            )
        } else {
            // Permission is already available, start camera preview
            requestWriteSdPermission(context)
        }
    }

    private fun requestWriteSdPermission(context: Activity) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            // Permission has not been granted and must be
            // requested.

            // Request the permission. The result will be received
            // in onRequestPermissionResult()
            ActivityCompat.requestPermissions(
                context, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_SD_WRITE
            )
        }
    }

    fun onRequestPermissionsResult(
        context: Activity,
        requestCode: Int,
        @NonNull permissions: Array<out String>,
        @NonNull grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                requestWriteSdPermission(context)
            }
        }
//        else if (requestCode == PERMISSION_REQUEST_SD_WRITE) {
////            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                // Permission has been granted. Start camera preview Activity.
////            } else {
////
////            }
//        }
    }

    fun setListViewHeight(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = 0
        var i = 0
        val len = listAdapter.count
        while (i < len) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
            i++
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }
}