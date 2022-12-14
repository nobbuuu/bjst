package com.tcl.base.kt

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.RegexUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.reflect.TypeToken
import com.tcl.base.R
import com.tcl.base.rxnetword.EncryptUtil
import com.tcl.base.rxnetword.parser.BaseEncryptResponse
import com.tcl.base.utils.AppGlobals
import com.tcl.base.utils.BigDecimalUtils
import rxhttp.map
import rxhttp.toClass
import rxhttp.wrapper.CallFactory
import rxhttp.wrapper.coroutines.Await
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @author : Yzq
 * time : 2020/11/16
 *
 */
const val SET_THEME = "set_theme"
const val MY_PAGE_SET_THEME_COLOR = "my_page_set_theme_color"

@Suppress("unused")
const val HOME_PAGE_CUT = "home_page_cut"
const val MAIN_PLAZA_CUT = "main_plaza_cut"

@Suppress("unused")
const val UPDATE_COLLECT_STATE = "update_collect_state"

//????????????
fun Context.packageInfo(): PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)

//????????????
fun Context.ktGetColor(colorRes: Int) = ContextCompat.getColor(this, colorRes)
fun View.ktGetColor(colorRes: Int) = context.ktGetColor(colorRes)

//????????????
fun Context.text(textRes: Int) = this.resources.getString(textRes)
fun View.text(textRes: Int) = context.text(textRes)

//??????????????????id
fun TypedValue.resourceId(resId: Int, theme: Resources.Theme): Int {
    theme.resolveAttribute(resId, this, true)
    return this.resourceId
}

//???????????????
fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = true): View {
    if (layoutId == -1) {
        return this
    }
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

inline fun <reified T : ViewModel> NavController.viewModel(@NavigationRes navGraphId: Int): T {
    val storeOwner = getViewModelStoreOwner(navGraphId)
    return ViewModelProvider(storeOwner)[T::class.java]
}

fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

/***
 * ?????????????????????View??????
 * @param delay Long ?????????????????????600??????
 * @return T
 */
@Suppress("unused")
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/***
 * ???????????????View??????
 * @param block: (T) -> Unit ??????
 * @return Unit
 */
@Suppress("unused", "UNCHECKED_CAST")
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
    block(it as T)
}

/***
 * ??????????????????????????????View??????
 * @param time Long ?????????????????????600??????
 * @param block: (T) -> Unit ??????
 * @return Unit
 */
fun <T : View> T.ktClick(time: Long = 600, block: () -> Unit) {
    triggerDelay = time
    ClickUtils.applyPressedBgDark(this)
    setOnClickListener {
        if (clickEnable()) {
            block()
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else -601
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 600
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

/***
 * ??????????????????????????????????????????[View.OnClickListener]
 * ??????????????????triggerDelay?????????600???????????????????????????
 */
interface OnLazyClickListener : View.OnClickListener {

    override fun onClick(v: View?) {
        if (v?.clickEnable() == true) {
            onLazyClick(v)
        }
    }

    fun onLazyClick(v: View)
}

/**
 * ??????Activity?????????
 * @param [brightness] 0 ~ 1
 */
fun Activity.setBrightness(brightness: Float) {
    val attributes = window.attributes
    attributes.screenBrightness = brightness
    window.attributes = attributes
}

val Float.ktToDp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
val Int.ktToDp
    get() = this.toFloat().ktToDp

val Int.ktDpToPx
    get() = this.toFloat().ktToDp.toInt()

val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
val Int.sp
    get() = this.toFloat().sp
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
val Int.dp
    get() = this.toFloat().dp


val Int.ktToColor
    get() = ContextCompat.getColor(AppGlobals.getApplication(), this)

val Int.toDrawable
    get() = ContextCompat.getDrawable(AppGlobals.getApplication(), this)
val Int.dpToInt
    get() = this.toFloat().ktToDp.toInt()

fun EditText.delayChangedText(time: Long, action: (text: String?) -> Unit) {

    addTextChangedListener(object : TextWatcher {
        val INPUT_MESSAGE_WHAT = 3
        val myHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                if (msg.what == INPUT_MESSAGE_WHAT) {
                    val inputContext = msg.obj as String
                    action(inputContext)
                }
            }
        }

        override fun afterTextChanged(text: Editable?) {
            myHandler.removeMessages(INPUT_MESSAGE_WHAT)
            val inputContentMsg = Message.obtain()
            inputContentMsg.what = INPUT_MESSAGE_WHAT
            inputContentMsg.obj = text?.toString()
            myHandler.sendMessageDelayed(inputContentMsg, time)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            //????????????????????????
//            action(text?.toString())

//            Handler().postDelayed({
//                //doSomethingHere()
//            }, 1000)
        }
    })
}


fun EditText.ktTextWatch(action: (text: String?) -> Unit) {

    addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(text: Editable?) {
            action.invoke(text?.toString())
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

/**
 * ????????????????????????
 * @param changeText ?????????????????????
 * @param colorRes ???????????????
 */
fun TextView.ktSetPartTextColor(
    source: String,
    changeText: String,
    colorRes: Int,
    spannableString: SpannableString? = null
) {
    var mSpannableString = spannableString
    if (mSpannableString == null) {
        mSpannableString = SpannableString(source)
    }
    val indexOf = source.indexOf(changeText)
    mSpannableString.setSpan(
        ForegroundColorSpan(colorRes.ktToColor),
        indexOf,
        indexOf + changeText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    text = mSpannableString
}

/**
 * ????????????????????????
 */
fun TextView.ktSetPartTextColor(
    source: String,
    changeTexts: Array<String>,
    colors: IntArray
) {
    if (changeTexts.size != colors.size) error("changeTexts???colors???????????????")
    val spannableString = SpannableString(source)
    repeat(changeTexts.size) {
        ktSetPartTextColor(source, changeTexts[it], colors[it], spannableString)
    }
    text = spannableString
}

/**
 * ???????????????
 */
fun View.showSoftInput() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * ???????????????
 */
fun View.hideSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Long.toDateTime(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)


///**
// * ???????????????????????????????????????then??????????????????????????????????????????
// * @return true-????????????false-?????????
// */
//fun Context.checkLogin(then: (() -> Unit)? = null): Boolean {
//    return if (isLogin()) {
//        then?.invoke()
//        true
//    } else {
//        this.startActivity<LoginActivity>()
//        false
//    }
//}
//
//fun isLogin(): Boolean = getLoginState() && CookieClass.hasCookie()


//???????????????RecyclerView
const val VERTICAL = 0
const val HORIZONTAL = 1
const val GRID = 2
const val FLEX_BOX = 3
fun RecyclerView.ktInit(
    bindAdapter: RecyclerView.Adapter<*>,
    layoutMangerType: Int = VERTICAL,
    spanCount: Int = 3,
    isDecoration: Boolean = false
): RecyclerView {
    layoutManager = when (layoutMangerType) {
        VERTICAL -> LinearLayoutManager(context)
        HORIZONTAL -> LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        GRID -> {
            val gridLayoutManager = GridLayoutManager(context, spanCount)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (bindAdapter.getItemViewType(position) == 0) {
                        return spanCount
                    }
                    return 1
                }
            }
            gridLayoutManager
        }
        FLEX_BOX -> {
            val flexBoxLayoutManger = FlexboxLayoutManager(context)
            //?????? ???????????????????????????????????????
            flexBoxLayoutManger.flexDirection = FlexDirection.ROW
            //?????????
            flexBoxLayoutManger.justifyContent = JustifyContent.FLEX_START
            flexBoxLayoutManger
        }

        else -> LinearLayoutManager(context)
    }
    adapter = bindAdapter


    if (isDecoration)
        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))


    return this
}

/**
 * ???????????????Code?????????????????????????????????
 */
fun String.ktChangeProductFamilyName(): String {
    return when (this) {
        "01" -> "??????"
        "02" -> "??????"
        "03" -> "??????"
        "04" -> "?????????"
        "05" -> "??????"
        "06" -> "????????????"
        "07" -> "??????"
        "08" -> "??????"
        "09" -> "??????"
        else -> "??????"
    }
}

/**
 * ??????ifValue ????????????????????????????????? ??? true -???trueResId    false-??? falseResId
 */
fun ImageView.ktSetImageIf(
    ifValue: Boolean,
    @DrawableRes trueResId: Int,
    @DrawableRes falseResId: Int
) {
    this.setImageResource(if (ifValue) trueResId else falseResId)
}


/**
 * View ???????????????
 */
fun View.ktVisually(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun BaseViewHolder.ktSetInVisible(@IdRes viewId: Int, isInVisible: Boolean): BaseViewHolder {
    val view = getView<View>(viewId)
    view.visibility = if (isInVisible) View.INVISIBLE else View.VISIBLE
    return this
}


fun BaseViewHolder.ktSetImage(@IdRes viewId: Int, imageUrl: String? = ""): BaseViewHolder {
    val iv = getView<ImageView>(viewId)
    var realUrl = imageUrl
    if (realUrl == null) {
        realUrl = ""
    }
    iv.load(realUrl) {
        size(ViewSizeResolver(iv))
        placeholder(R.mipmap.ic_norm_goods)
        error(R.mipmap.ic_norm_goods)
    }
    return this
}


fun Double.ktSetNo0Str(): String {
    return BigDecimal(BigDecimalUtils.formatMoney(this.toString())).stripTrailingZeros()
        .toPlainString()
}


/**
 * ??????EditText??????????????????
 */
@Suppress("UNUSED_ANONYMOUS_PARAMETER")
fun EditText.ktAddEditTextInhibitInputSpeChat() {
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        val speChat = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/??????????????????????????????????????????????????????]"
        val pattern: Pattern = Pattern.compile(speChat)
        val matcher: Matcher = pattern.matcher(source.toString())
        if (matcher.find()) "" else null
    }
    this.filters = arrayOf(filter)
}


fun Double.ktToString(): String {
    val nf: NumberFormat = NumberFormat.getInstance()
    nf.isGroupingUsed = false
    nf.format(this)
    return nf.format(this)
}

var typeFilter =
    InputFilter { source, start, end, dest, dstart, dend ->
        val regx = "^[0-9a-zA-Z\\u4e00-\\u9fa5@\\[\\]??????()?????????#??? !????/-]+\$"
        if (RegexUtils.isMatch(
                regx,
                source.toString()
            ) || source.isEmpty()
        ) regx else {
            "??????????????????????????????".ktToastShow()
            regx
        }
    }
