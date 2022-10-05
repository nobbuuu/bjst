package com.dream.bjst.net

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.account.bean.ChatMessageBean
import com.dream.bjst.account.bean.PrivacyBean
import com.dream.bjst.bean.BaseParamBean
import com.dream.bjst.bean.DeviceUpStatusBean
import com.dream.bjst.bean.LoginBean

import com.dream.bjst.identification.bean.BankListBean
import com.dream.bjst.identification.bean.ConfirmResultBean
import com.dream.bjst.identification.bean.IdCardDetailsBean
import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.loan.bean.*
import com.dream.bjst.bean.UpgradeDialogBean
import com.dream.bjst.other.toBoolean
import com.dream.bjst.repayment.bean.*
import com.dream.bjst.utils.DeviceUtils
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse


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
            .addAll(GsonUtils.toJson(QueryProductBean(DeviceUtils.getLocalIPAddress())))
            .toResponse<LoanInfoBean>()
            .await()

    }

    /**
     * 获取客户产品信用额度及当前待还金额
     */
    suspend fun fetchCreditAmount(): LoanPreBean {
        //core/product/fetchProducts
        return RxHttp.postJson("/DB979B8691DB84869B90819780DB929180979CB78691909D80B5999B819A80")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<LoanPreBean>()
            .await()

    }

    /**
     * 批量申请贷款
     */
    suspend fun apply(param: String): ApplyResultBean {
        //core/borrow/apply
        return RxHttp.postJson("/DB979B8691DB969B86869B83DB958484988D")
            .addAll(param)
            .toResponse<ApplyResultBean>()
            .await()

    }

    /**
     * 获取历史订单记录
     */
    suspend fun fetchOrderHistory(param: String): OrderResultBean {
        //core/borrow/fetchOrderHistory
        return RxHttp.postJson("/DB979B8691DB969B86869B83DB929180979CBB86909186BC9D87809B868D")
            .addAll(param)
            .toResponse<OrderResultBean>()
            .await()

    }

    /**
     * 获取处理中的订单数量
     */
    suspend fun fetchProcessingOrderCount(): String {
        //core/borrow/fetchProcessingOrderCount
        return RxHttp.postJson("/DB979B8691DB969B86869B83DB929180979CA4869B979187879D9A93BB86909186B79B819A80")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<String>()
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

    /**
     *在线还款
     */
    suspend fun reqPayData(param: String): requestRepaymentBean {
        return RxHttp.postJson("/DB979B8691DB84958DDB929180979CA69184958DB89D9A9F")
            .addAll(param)
            .toResponse<requestRepaymentBean>()
            .await()

    }

    /**
     *版本更新
     */
    suspend fun upGradeData(): UpgradeDialogBean {
        return RxHttp.postJson("/DB979B8691DB958484DB929180979CB58484A29186879D9B9AA2C6")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<UpgradeDialogBean>()
            .await()

    }

    /**
     *判断客户是否完成设备信息上传(详细信息)
     */
    suspend fun hadUploadDeviceInfoDetails(): DeviceUpStatusBean {
        //core/device/hadUploadDeviceInfoDetails
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB9C9590A184989B9590B091829D9791BD9A929BB09180959D9887")
            .addAll(GsonUtils.toJson(BaseParamBean()))
            .toResponse<DeviceUpStatusBean>()
            .await()

    }

    /**
     *上传设备信息(相册信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    suspend fun uploadDeviceAlbumInfo(param: String): ConfirmResultBean {
        //core/device/uploadDeviceAlbumInfo
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB8184989B9590B091829D9791B598968199BD9A929B")
            .addAll(param)
            .add("isEncryptBody", false)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     *上传设备信息(基础信息，必须获取的字段已标注)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    suspend fun uploadDeviceBaseInfo(param: String): ConfirmResultBean {
        //core/device/uploadDeviceBaseInfo
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB8184989B9590B091829D9791B6958791BD9A929B")
            .addAll(param)
            .add("isEncryptBody", false)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     *上传设备信息(位置信息)
     */
    suspend fun uploadDeviceLocation(param: String): ConfirmResultBean {
        //core/device/uploadDeviceLocation
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB8184989B9590B091829D9791B89B9795809D9B9A")
            .addAll(param)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     *上传设备信息(短信信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    suspend fun uploadDeviceSmsInfo(param: String): ConfirmResultBean {
        //core/device/uploadDeviceSmsInfo
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB8184989B9590B091829D9791A79987BD9A929B")
            .addAll(param)
            .add("isEncryptBody", false)
            .toResponse<ConfirmResultBean>()
            .await()
    }

    /**
     *上传设备信息(联系人信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    suspend fun uploadDeviceContactsInfo(param: String): ConfirmResultBean {
        //core/device/uploadDeviceTxlInfo
        return RxHttp.postJson("/DB979B8691DB9091829D9791DB8184989B9590B091829D9791A08C98BD9A929B")
            .addAll(param)
            .add("isEncryptBody", false)
            .toResponse<ConfirmResultBean>()
            .await()
    }
}