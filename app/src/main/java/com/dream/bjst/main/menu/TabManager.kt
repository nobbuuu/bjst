package com.dream.bjst.main.menu

import androidx.annotation.IntDef
import com.dream.bjst.R

/**
 * Author: tiaozi
 * Date : 2021/6/9
 * Drc:
 */
const val MAIN_TAB_AREA = 0
const val MAIN_TAB_SQUARE = 1
const val MAIN_TAB_MESSAGE = 2
const val MAIN_TAB_MINE = 3
const val KEY_TAB_POSITION = "position"

object TabManager {
    val menus: List<TabItem>
        get() {
            return arrayListOf<TabItem>().apply {
                add(
                    TabItem(
                        MAIN_TAB_AREA,
                        R.id.tab_lottie_1,
                        R.string.title_area,
                        "lottie_home.json",
                        selectImg = R.mipmap.home_select,
                        normalImg = R.mipmap.home_normal
                    )
                )
                add(
                    TabItem(
                        MAIN_TAB_SQUARE,
                        R.id.tab_lottie_2,
                        R.string.title_square,
                        "lottie_category.json",
                        selectImg = R.mipmap.payment_select,
                        normalImg = R.mipmap.payment_normal
                    )
                )
                add(
                    TabItem(
                        MAIN_TAB_MINE,
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

@IntDef(MAIN_TAB_AREA, MAIN_TAB_SQUARE, MAIN_TAB_MESSAGE, MAIN_TAB_MINE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TabId