package com.dream.bjst.net.parser

import java.io.Serializable

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
class PageList<T> :Serializable{
    var totalNum = 0//总条数

    var list: List<T> = arrayListOf()
}