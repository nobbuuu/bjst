package com.dream.bjst.net

import com.dream.bjst.bean.UserInfo
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse


/**
 * @author : tiaozi
 * time : 2020/11/9 17:30
 */
object Api {

    /**
     * 获取全国各省市区地址
      */
    suspend fun getAdsPictures(): List<UserInfo> {
        return RxHttp.get("/api/areaBase/getAreaBase")
            .add("grade","3")
            .add("removeAll","false")
            .toResponse<List<UserInfo>>()
            .await()
    }


}