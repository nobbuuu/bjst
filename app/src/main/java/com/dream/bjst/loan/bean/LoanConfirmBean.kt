package com.dream.bjst.loan.bean

data class LoanConfirmBean(
    var loanAmount: String,
    var repayAmount: String,
    var dueDate: String,
    var products: List<ProductListBean>?
)
