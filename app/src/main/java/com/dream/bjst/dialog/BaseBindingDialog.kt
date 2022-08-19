package com.dream.bjst.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.dream.bjst.R
import java.lang.reflect.ParameterizedType

open class BaseBindingDialog<B : ViewBinding>(
    context: Context,
    val layoutId: Int = 0,
    val styleRes: Int = R.style.ActionSheetDialogStyle
) :
    Dialog(context, styleRes) {
    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[type.actualTypeArguments.size - 1] as Class<*>
            when {
                ViewBinding::class.java.isAssignableFrom(cls) && cls != ViewDataBinding::class.java -> {
                    cls.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                        @Suppress("UNCHECKED_CAST")
                        mBinding = it.invoke(null, layoutInflater) as B
                        setContentView(mBinding.root)
                    }
                }
                else -> {
                    if (layoutId == 0) throw IllegalArgumentException("If you don't use ViewBinding, you need to override method layoutId")
                    setContentView(layoutId)
                }
            }
        } else throw IllegalArgumentException("Generic error")
        val window: Window? = window
        if (window != null) {
            val attr: WindowManager.LayoutParams = window.attributes
            if (attr != null) {
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT
                attr.gravity = Gravity.CENTER
                window.setAttributes(attr)
            }
        }
    }
}