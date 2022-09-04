package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.databinding.DialogRelationBinding
import com.dream.bjst.identification.adapter.RelationAdapter
import com.dream.bjst.identification.bean.RelationBean
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class RelationDialog(context: Context, val block: ((String) -> Unit)? = null) :
    BaseBindingDialog<DialogRelationBinding>(context, location = Gravity.BOTTOM) {

    val relations = listOf("Father/Mother", "Husband/Wife", "Son/Daughter", "Friend")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = arrayListOf<RelationBean>()
        relations.forEach {
            data.add(RelationBean(it, false))
        }
        val relationAdapter = RelationAdapter {
            dismiss()
            block?.invoke(it)
        }
        mBinding.relationRv.apply {
            addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.VERTICAL))
            adapter = relationAdapter
        }
        relationAdapter.setList(data)
    }
}