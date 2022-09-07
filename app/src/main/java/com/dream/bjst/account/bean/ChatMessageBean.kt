package com.dream.bjst.account.bean

/**
 * 创建日期：2022-09-06 on 10:58
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
data class ChatMessageBean(
    val `978187809B999186B7958691B79C9580A495809C`: String, //客服聊天地址，获取到地址后拼接这些参数：customerId=【值】&userName=【值】&userPhone=【值】&customerUId=【值】。在webview里面打开url
    val `978187809B999186B7958691A49C9B9A91`: String, //客服电话
    val `978187809B999186A79186829D9791A7839D80979C`: Boolean, //自研客服系统是否开启 true开启 false关闭
    val `839C9580B5848487A49C9B9A91`: String  //whatApp号码
)