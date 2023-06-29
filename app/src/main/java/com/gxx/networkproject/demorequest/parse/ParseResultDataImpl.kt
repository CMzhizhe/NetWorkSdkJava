package com.gxx.networkproject.demorequest.parse

import android.util.Log
import com.google.gson.JsonElement
import com.gxx.networklibrary.networkpackge.apiservice.impl.AbsOnRequestResultImpl
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener
import com.gxx.networkproject.BuildConfig
import com.gxx.networkproject.demorequest.basemodel.BaseBean
import com.gxx.networkproject.utils.MoshiUtil
import com.squareup.moshi.JsonAdapter
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/22/022
 * @description TODO 当前类，处理成功，失败，开发者可以自行修改
 **/
open class ParseResultDataImpl<T> : AbsOnRequestResultImpl() {
    private val TAG = "AbsDataResultCallback"
    private val RESPONSE_STATUS_STATUS_NEGATIVE_201="-201";//json解析失败

    override fun onRequestFail(
        throwable: Throwable?,
        status: String?,
        failMsg: String?,
        respon: String?,
        onIParserListener: OnIParserListener?
    ) {
        super.onRequestFail(throwable, status, failMsg, respon, onIParserListener)
        if (throwable!=null){
            if (throwable is SocketTimeoutException || throwable is ConnectException || throwable is UnknownHostException) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "出现网络异常")
                }
            }
        }
        if (onIParserListener!=null){
            onRequestDataFail(status?:"", failMsg?:"", onIParserListener as BaseBean)
            onRequestBaseBeanFail(onIParserListener as BaseBean)
        }
    }

    override fun onRequestSuccess(
        method: String?,
        targetElement: JsonElement?,
        onIParserListener: OnIParserListener?
    ) {
        super.onRequestSuccess(method,targetElement,onIParserListener)
        if (onIParserListener== null){
            return
        }
        if (targetElement!=null){
            var result:Any?=null
            try {
                val parameterizedType = this::class.java.genericSuperclass as ParameterizedType
                val subType =  parameterizedType.actualTypeArguments.first() //获取泛型T
                val adapter: JsonAdapter<Any> = MoshiUtil.moshi.adapter(subType)
                result = adapter.fromJson(targetElement.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                //处理解析异常
                onRequestFail(null,RESPONSE_STATUS_STATUS_NEGATIVE_201,"解析异常", null,onIParserListener)
                return
            }
            onRequestDataSuccess(if (result == null) null else result as T)
            onRequestBaseBeanSuccess(if (result == null) null else result as T,
                onIParserListener as BaseBean
            )
        }else{
            onRequestDataSuccess(null)
            onRequestBaseBeanSuccess(null, onIParserListener as BaseBean)
        }
    }


    /**
     * @date 2022/5/7
     * @auther qinzhichang
     * @Descriptiion 请求失败
     **/
    open fun onRequestDataFail(code: String, msg: String, baseBean: BaseBean) {}

    /**
     * @date 2022/6/23
     * @author qinzhichang
     * @Description 请求失败
     **/
    open fun onRequestBaseBeanFail(baseBean: BaseBean) {}

    /**
     * @date 2022/5/7
     * @auther qinzhichang
     * @Descriptiion 请求成功
     * 返回最终的结果T
     **/
    open fun onRequestDataSuccess(data: T?) {}

    /**
     * @date 2022/5/7
     * @auther qinzhichang
     * @Descriptiion 请求成功
     * 返回含有 BaseBean 的
     **/
    open fun onRequestBaseBeanSuccess(data: T?, baseBean: BaseBean) {}


}