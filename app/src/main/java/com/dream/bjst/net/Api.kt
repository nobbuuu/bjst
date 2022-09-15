package com.dream.bjst.net

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.account.bean.AccountDeleteBean
import com.dream.bjst.account.bean.ChatMessageBean
import com.dream.bjst.account.bean.PrivacyBean
import com.dream.bjst.bean.BaseParamBean
import com.dream.bjst.bean.LoginBean

import com.dream.bjst.identification.bean.BankListBean
import com.dream.bjst.identification.bean.ConfirmResultBean
import com.dream.bjst.identification.bean.IdCardDetailsBean
import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.loan.bean.HomeInfoBean
import com.dream.bjst.loan.bean.LoanInfoBean
import com.dream.bjst.net.parser.JsonUtil
import com.dream.bjst.net.parser.Response
import com.dream.bjst.other.toBoolean
import com.dream.bjst.repayment.bean.ExtendRePaymentBean
import com.dream.bjst.repayment.bean.PaymentUtrBean
import com.dream.bjst.repayment.bean.RepaymentBean
import com.dream.bjst.repayment.bean.RepaymentInDetailBean
import com.google.gson.reflect.TypeToken
import com.tcl.base.rxnetword.EncryptUtil
import com.tcl.base.rxnetword.parser.BaseEncryptResponse
import rxhttp.map
import rxhttp.toClass
import rxhttp.toStr
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse
import rxhttp.wrapper.utils.convert
import java.lang.reflect.Type


/**
 * @author : tiaozi
 * time : 2020/11/9 17:30
 */
object Api {

    /**
     * 获取验证码
     */
    suspend fun getAuthCode(param: String): Map<String, String> {
        //customer/otp
        return RxHttp.postJson("/DB978187809B999186DB9B8084")
            .addAll(param)
            .toResponse<Map<String, String>>()
            .await()
    }

    /**
     * 验证码登录/注册接口
     */
    suspend fun loginOrRegByOtp(param: String): LoginBean {
        //customer/loginOrRegByOtp
        return RxHttp.postJson("/DB978187809B999186DB989B939D9ABB86A69193B68DBB8084")
            .addAll(param)
            .toResponse<LoginBean>()
            .await()
    }

    /**
     * 上传身份证前照
     */

    suspend fun idCardFrontOcr(param: String): IdCardDetailsBean {
        //customer/kyc/idCardFrontOcr
        return RxHttp.postJson("/DB978187809B999186DB9F8D97DB9D90B7958690B2869B9A80BB9786")
            .addAll(param)
            .toResponse<IdCardDetailsBean>()
            .await()
    }

    /**
     * 上传身份证背照
     */

    suspend fun idCardBackOcr(param: String): IdCardDetailsBean {
        //customer/kyc/idCardBackOcr
        return RxHttp.postJson("/DB978187809B999186DB9F8D97DB9D90B7958690B695979FBB9786")
            .addAll(param)
            .toResponse<IdCardDetailsBean>()
            .await()
    }

    /**
     * 上传PAN卡照片
     */

    suspend fun panOcr(param: String): IdCardDetailsBean {
        //customer/kyc/panOcr
        return RxHttp.postJson("/DB978187809B999186DB9F8D97DB84959ABB9786")
            .addAll(param)
            .toResponse<IdCardDetailsBean>()
            .await()
    }

    /**
     * 获取客户kyc进行到的步骤
     */

    suspend fun fetchCustomerKycStatus(): IdCardStatusBean {
        //customer/fetchCustomerKycStatus
        return RxHttp.postJson("/DB978187809B999186DB929180979CB78187809B999186BF8D97A78095808187")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<IdCardStatusBean>()
            .await()
    }

    /**
     * 获取证件ocr后的信息
     */

    suspend fun fetchCustomerIdCardInfo(): IdCardDetailsBean {
        //customer/extension/fetchCustomerIdCardInfo
        return RxHttp.postJson("/DB978187809B999186DB918C80919A879D9B9ADB929180979CB78187809B999186BD90B7958690BD9A929B")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<IdCardDetailsBean>()
            .await()
    }

    /**
     * 身份证ocr界面-提交客户调整后的基础信息
     */

    suspend fun submitAdjustInfo(param: String): ConfirmResultBean {
        //customer/kyc/submitAdjustInfo
        return RxHttp.postJson("/DB978187809B999186DB9F8D97DB878196999D80B5909E818780BD9A929B")
            .addAll(param)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     * 上传人脸识别的图片
     */

    suspend fun submitPictureInfo(param: String): ConfirmResultBean {
        ///core/app/fetchLiveNessCompany
        return RxHttp.postJson("/DB978187809B999186DB9F8D97DB979C91979FB89D8291BA918787B59A90B2959791B79B9984958691B68DB597979581809C")
            .addAll(param)
            .toResponse<ConfirmResultBean>()
            .await()

    }

    /**
     * 提交紧急联系人
     */
    suspend fun pushUrgencyContact(param: String): Boolean {
        //customer/extension/pushUrgencyContact
        return RxHttp.postJson("/DB978187809B999186DB918C80919A879D9B9ADB8481879CA18693919A978DB79B9A80959780")
            .addAll(param)
            .toBoolean()
            .await()
    }

    /**
     * 获取银行列表
     */
    suspend fun fetchBanks(): List<BankListBean> {
        //customer/bank/fetchBanks
        return RxHttp.postJson("/DB978187809B999186DB96959A9FDB929180979CB6959A9F87")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<List<BankListBean>>()
            .await()
    }

    /**
     * 认证并绑定银行卡
     */
    suspend fun customerBindBankCard(param: String): ConfirmResultBean {
        //customer/bank/customerBindBankCard
        return RxHttp.postJson("/DB978187809B999186DB96959A9FDB978187809B999186B69D9A90B6959A9FB7958690")
            .addAll(param)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     * 请求删除用户数据
     */
    suspend fun deleteAccountData(): Boolean {
        return RxHttp.postJson("/DB978187809B999186DB8691999B8291B78187809B999186BD9A929B")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toBoolean()
            .await()

    }

    /**
     * 聊天拼接
     */
    suspend fun chatMessage(): ChatMessageBean {
        return RxHttp.postJson("/DB979B8691DB958484DB929180979CB78187809B999186B7958691BD9A929B")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<ChatMessageBean>()
            .await()

    }

    /**
     * 隐私协议
     */
    suspend fun privacyContent(): PrivacyBean {
        return RxHttp.postJson("/DB979B8691DB958484DB929180979CB59386919199919A80")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<PrivacyBean>()
            .await()

    }

    /**
     * 获取首页需要展示的借贷相关信息
     */
    suspend fun fetchHomeInfo(): HomeInfoBean {
        //core/home/fetchHomeInfo
        return RxHttp.postJson("/DB979B8691DB9C9B9991DB929180979CBC9B9991BD9A929B")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<HomeInfoBean>()
            .await()

    }

    /**
     * 获取首页产品列表
     */
    suspend fun fetchProducts(): LoanInfoBean {
        //core/product/fetchProducts
        return RxHttp.postJson("/DB979B8691DB84869B90819780DB929180979CA4869B9081978087")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<LoanInfoBean>()
            .await()

    }

    /**
     * 还款首页
     */

    suspend fun repayment(): RepaymentBean {
        // /core/pay/getRepayPageInfo
        return RxHttp.postJson("/DB979B8691DB84958DDB939180A69184958DA4959391BD9A929B")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<RepaymentBean>()
            .await()
    }


    /**
     * 还款详情界面
     */

    suspend fun repaymentDetail(param: String): RepaymentInDetailBean {
        // /core/pay/getRepayPageInfo
        return RxHttp.postJson("/DB979B8691DB84958DDB939180A69184958DB68DB69B86869B83BD90")
            .addAll(param)
            .toResponse<RepaymentInDetailBean>()
            .await()
    }
    /**
     * 延期还款界面
     */
    suspend fun paymentExtend(param: String): ExtendRePaymentBean {
        // /core/pay/getRepayPageInfo
        return RxHttp.postJson("/DB979B8691DB84958DDB929180979CA69B9898A691A4958DBD9A929B")
            .addAll(param)
            .toResponse<ExtendRePaymentBean>()
            .await()
    }
    /**
     *post UTR数字和图片
     */
    suspend fun payUTRData(param: String): PaymentUtrBean {
        return RxHttp.postJson("/DB979B8691DB818086DB878196999D80A18086")
            .addAll(param)
            .toResponse<PaymentUtrBean>()
            .await()

    }
}