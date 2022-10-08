package com.dream.bjst.identification.bean

import java.io.Serializable

data class IdentifyBean(
    val icon: Int,
    var name: String,
    var isApproved: Boolean = false
):Serializable
