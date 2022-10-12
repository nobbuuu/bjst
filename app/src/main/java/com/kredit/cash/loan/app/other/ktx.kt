package com.kredit.cash.loan.app.other

import rxhttp.map
import rxhttp.wrapper.CallFactory
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.toResponse


fun CallFactory.toBoolean(isEqualsIgnoreCase: Boolean=false): Await<Boolean> {
    return this.toResponse<String>().map {
        if (isEqualsIgnoreCase) {
            it.lowercase() == "true"
        } else {
            it == "true"
        }
    }
}


