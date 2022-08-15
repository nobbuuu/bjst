package com.dream.bjst.bean

data class RepaymentBean(
    val overdued: List<OverduedBean>,
)

data class OverduedBean(
    val timeLimit: String,
)
