package com.kredit.cash.loan.app.loan.bean

data class OrderResultBean(
    val `809B809598B79B819A80`: Int? = 1,
    val applyTime: String,
    val `9B86909186B89D8780`: List<HistoryBean>? = listOf()
)

data class HistoryBean(
    val `84869B90819780BA959991`: String,
    val `84869B90819780BD979B`: String,
    val `918C849D8695809D9B9AA09D9991`: String,
    val `958484988DA09D9991`: String,
    val `989B959AB5999B819A80`: Int,
    val `9B86909186A78095808187`: Int?,
    val `9B86909186BA8199969186`: String
)