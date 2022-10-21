package com.kredit.cash.loan.app.liveness

import android.hardware.Camera


/**
 * Created by Win on 2022/10/20
 */
object CameraPhysics {

    private const val defaultWidth = 1280
    private const val defaultHeight = 720
    var previewWidth = defaultWidth
    var previewHeight = defaultHeight

    fun initCameraPhysics(parameters: Camera.Parameters) {
        val supportedPreviewSizes = parameters.supportedPreviewSizes
        var has1280 = false
        var has640 = false
        supportedPreviewSizes.forEach {
            if (it.width == 1280 && it.height == 720) {
                has1280 = true
            } else if (it.width == 640 && it.height == 480) {
                has640 = true
            }
        }
        if (has1280) {
            previewWidth = 1280
            previewHeight = 720
        } else if (has640) {
            previewWidth = 640
            previewHeight = 480
        } else if (supportedPreviewSizes.size > 0) {
            previewWidth = supportedPreviewSizes[0].width
            previewHeight = supportedPreviewSizes[0].height
        }
    }
}