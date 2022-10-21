package com.kredit.cash.loan.app.liveness

import com.dfsdk.liveness.DFLivenessSDK.DFLivenessImageResult
import java.io.Serializable

/**
 * Created by Win on 2022/10/21
 */
data class LivenessResult(
    val encryptResult: ByteArray?,
    val faceImages: Array<DFLivenessImageResult>?,
):Serializable