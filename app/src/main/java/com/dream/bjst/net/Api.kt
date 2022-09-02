package com.dream.bjst.net

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.bean.BaseParamBean
import com.dream.bjst.bean.LoginBean
import com.dream.bjst.identification.bean.ConfirmResultBean
import com.dream.bjst.identification.bean.DetectionPictureBean
import com.dream.bjst.identification.bean.IdCardDetailsBean
import com.dream.bjst.identification.bean.IdCardStatusBean
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

    suspend fun submitPictureInfo(param: String):DetectionPictureBean {
        ///core/app/fetchLiveNessCompany
          return RxHttp.postJson("DB979B8691DB958484DB929180979CB89D8291BA918787B79B9984959A8D")
              .addAll(param)
              .toResponse<DetectionPictureBean>()
              .await()
    }
}