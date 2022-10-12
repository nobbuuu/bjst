package com.kredit.cash.loan.app.dialog

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kredit.cash.loan.app.databinding.DialogRelationBinding
import com.kredit.cash.loan.app.identification.adapter.RelationAdapter
import com.kredit.cash.loan.app.identification.bean.RelationBean
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class RelationDialog(val mContext: Activity, val block: ((String,Int) -> Unit)? = null) :
    BaseBindingDialog<DialogRelationBinding>(mContext) {
    val relations = listOf("Father/Mother", "Husband/Wife", "Son/Daughter", "Friend")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arrayListOf<RelationBean>()
        relations.forEach {
            data.add(RelationBean(it, false))
        }
        val relationAdapter = RelationAdapter { it,position ->
            dismiss()
            block?.invoke(it,position)
        }
        mBinding.relationRv.apply {
            addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.VERTICAL))
            adapter = relationAdapter
        }
        relationAdapter.setList(data)
    }
}