package com.gxx.networklibrary.networkpackge.apiservice.impl

import com.google.gson.JsonElement
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestFailListener
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestSuccessListener
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener

/**
 * @date 创建时间: 2021/11/21
 * @auther gaoxiaoxiong
 * @description 请求结果集体处理
 */
abstract class AbsOnRequestResultImpl : OnRequestSuccessListener, OnRequestFailListener {

    /**
     * @date 创建时间: 2022/12/11
     * @author gaoxiaoxiong
     * @description 失败回调
     */
    override fun onRequestFail(
        throwable: Throwable?,
        status: String?,
        failMsg: String?,
        respon: String?,
        onIParserListener: OnIParserListener?
    ) {

    }


    /**
     * @date 创建时间: 2021/11/21
     * @auther gaoxiaoxiong
     * @description 处理成功
     */
    override fun onRequestSuccess(
        method: String?,
        targetElement: JsonElement?,
        onIParserListener: OnIParserListener?
    ) {

    }
}