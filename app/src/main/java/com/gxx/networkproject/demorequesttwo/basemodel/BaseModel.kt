package com.gxx.networkproject.demorequesttwo.basemodel

import com.google.gson.JsonElement
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener

/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/29/029
 * @description
 * @param method 接口名称
 * @param resourceJsonString 服务器提供的整个原始数据
 * @param targetJsonElement 目标JsonElement，就是解析出的data里面的数据
 **/
class BaseModel(
    var method: String? = null,
    var resourceJsonString: String? = null,
    var targetJsonElement: JsonElement? = null
) : OnIParserListener {
    var message: String? = null
    var nu: String? = null
    var ischeck: String? = null
    var com: String? = null
    var status: String? = null
    var condition: String? = null
    var state: String? = null
    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  解析出的data里面的数据
     **/
    override fun onTargetJsonElement(): JsonElement? {
        return targetJsonElement
    }

    override fun onResourceJsonString(): String? {
        return resourceJsonString
    }

    override fun isSuccess(): Boolean {
       return status == "200"
    }

}