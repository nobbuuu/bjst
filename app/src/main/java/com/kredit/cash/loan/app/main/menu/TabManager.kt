package com.kredit.cash.loan.app.main.menu

import androidx.annotation.IntDef
import com.kredit.cash.loan.app.R

/**
 * Author: tiaozi
 * Date : 2021/6/9
 * Drc:
 */
const val MAIN_TAB_LOAN = 0
const val MAIN_TAB_REPAYMENT = 1
const val MAIN_TAB_ACCOUNT = 2
const val KEY_TAB_POSITION = "position"

object TabManager {
    val menus: List<TabItem>
        get() {
            return arrayListOf<TabItem>().apply {
                add(
                    TabItem(
                        MAIN_TAB_LOAN,
                        R.id.tab_lottie_1,
                        R.string.title_area,
                        "lottie_home.json",
                        selectImg = R.mipmap.home_select,
                        normalImg = R.mipmap.home_normal
                    )
                )
                add(
                    TabItem(
                        MAIN_TAB_REPAYMENT,
                        R.id.tab_lottie_2,
                        R.string.title_square,
                        "lottie_category.json",
                        selectImg = R.mipmap.payment_select,
                        normalImg = R.mipmap.payment_normal
                    )
                )
                add(
                    TabItem(
                        MAIN_TAB_ACCOUNT,
                        R.id.tab_lottie_5,
                        R.string.title_mine,
                        "lottie_mine.json",
                        selectImg = R.mipmap.mine_select,
                        normalImg = R.mipmap.mine_normal
                    )
                )
            }
        }
}

@IntDef(MAIN_TAB_LOAN, MAIN_TAB_REPAYMENT, MAIN_TAB_ACCOUNT)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TabId