package com.kredit.cash.loan.app.main.menu

import androidx.annotation.StringRes

/**
 * Author: tiaozi
 * Date : 2021/6/21
 * Drc:
 */
data class TabItem(
    @TabId val position: Int,
    val id: Int,
    @StringRes val text: Int,
    val lottieFile: String,
    val normalImg: Int,
    val selectImg: Int,
    val unRead: Boolean = false,
    val unReadCount: Int = 0
)