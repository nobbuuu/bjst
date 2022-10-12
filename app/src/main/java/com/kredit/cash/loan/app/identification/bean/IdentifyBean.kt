package com.kredit.cash.loan.app.identification.bean

import java.io.Serializable

data class IdentifyBean(
    val icon: Int,
    var name: String,
    var isApproved: Boolean = false
):Serializable
