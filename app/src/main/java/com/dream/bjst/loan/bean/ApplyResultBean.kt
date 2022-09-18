package com.dream.bjst.loan.bean

data class ApplyResultBean(
    val `869187B29180979CA4869B90819780`: LoanInfoBean,
    val `90809BB69B86869B83B58484988DA6918781988087`: List<ApplyResultListBean>? = null,
    val `908191B0958091`: String? = null,
    val `958484988DB69580979CBA9B`: String? = null
)

data class ApplyResultListBean(
    val `84869B90819780BD90`: Int,
    val `969B86869B83B58484988DBD90`: Int
)