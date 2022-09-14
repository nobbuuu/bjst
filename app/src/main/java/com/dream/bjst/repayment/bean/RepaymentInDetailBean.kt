package com.dream.bjst.repayment.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class RepaymentInDetailBean(
    val `969B86869B83BD90`: String,//订单id
    val `9D979BA18698`: String,//图标url
    val `9B86909186A78095808187`: String?,//订单状态(10：未到期订单；20：当日到期订单；30：逾期订单；)
    val `84869D9A979D849598B5999B819A80`: String,//本金（即借款金额，=用户实际收款+扣除的服务费+扣除的税费）
    val `84869B90819780BD90`: String,//产品编号
    val `84869B90819780BA959991`: String,//产品名称
    val `869199959D9ABB829186908191`: String,//逾期金额/剩余滞纳金
    val `869199959D9AA09B809598B5999B819A80`: String,//剩余总待还金额
    val `869184958DB19A90`: String//正常还款的开始时间（还款时小于此时间为提前还款）
):Serializable,BaseParamBean()

