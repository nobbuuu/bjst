package com.dream.bjst.identification.bean

import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo
import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class DetectionPictureParam(
    val customerId: Int,
    val marketId: Int
) :BaseParamBean(), Serializable