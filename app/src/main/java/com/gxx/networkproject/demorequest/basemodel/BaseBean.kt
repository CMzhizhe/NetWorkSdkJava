package com.gxx.networkproject.demorequest.basemodel

import com.google.gson.JsonElement
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener

/**
 * @date 2022/4/24
 * @Description:
 * @param method 请求的接口名称
 * @param resourceJsonString 源数据
 * @param jsonElement 转换成jsonObj jsonArray，转换成具体的类型的Json
 */
class BaseBean(var method: String? = null,var resourceJsonString: String? = null,var jsonElement: JsonElement? = null): OnIParserListener {
    override fun onTargetJsonElement(): JsonElement? {
       return jsonElement
    }

    override fun onResourceJsonString(): String? {
       return resourceJsonString;
    }

    override fun isSuccess(): Boolean {
        return true
    }
}