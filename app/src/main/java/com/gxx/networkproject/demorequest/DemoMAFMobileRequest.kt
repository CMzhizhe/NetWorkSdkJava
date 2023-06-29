package com.gxx.networkproject.demorequest

import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener
import com.gxx.networklibrary.networkpackge.apiservice.base.AbsMAFApiManager
import com.gxx.networklibrary.networkpackge.apiservice.base.AbsMAFMobileRequest
import com.gxx.networklibrary.networkpackge.apiservice.base.BAFABSMAFMobileRequest.OnMAFApiManagerListener
import com.gxx.networklibrary.networkpackge.apiservice.base.BAFABSMAFMobileRequest.OnRequestResultCallBackConfigListener
import com.gxx.networkproject.constant.Constant
import com.gxx.networkproject.demorequest.factory.FactoryImpl
import com.gxx.networkproject.demorequest.parse.ParseResultDataImpl
import com.gxx.networkproject.demorequest.parse.ResponseTransform

/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/22/022
 * @description  TODO  当前类，调用者可以自行定义
 */
class DemoMAFMobileRequest private constructor() {
    private var mAbsMAFApiManagerImpl: AbsMAFApiManagerImpl? = null
    private val mAbsMAFMobileRequestImpl: AbsMAFMobileRequestImpl = AbsMAFMobileRequestImpl()
    private val mResponseTransform = ResponseTransform()

    companion object {
        private var mDemoMAFMobileRequest: DemoMAFMobileRequest? = null
        fun getInstance(): DemoMAFMobileRequest {
            if (mDemoMAFMobileRequest == null) {
                mDemoMAFMobileRequest = DemoMAFMobileRequest()
            }
            return mDemoMAFMobileRequest!!
        }
    }

    init {
        val builder = AbsMAFApiManager.Builder()
            .setCostTime(2 * 1000L) //设置方法请求超时接口回调
            .setRequestUrl(Constant.BASE_REQUEST) //设置base Url
            .setRetryOnConnectionFailure(false) //是否重新连接
            .setIsDevmodel(true) //是否开发者模式，开发者模式，会打印网络请求日志
            .setOnFactoryListener(FactoryImpl())
            .setOnObservableSourceStringListener(mResponseTransform)
        mAbsMAFApiManagerImpl = AbsMAFApiManagerImpl(builder)
        mAbsMAFMobileRequestImpl.setOnMAFApiManagerListener(mAbsMAFApiManagerImpl)
    }

    private class AbsMAFApiManagerImpl(absBuilder: Builder) : AbsMAFApiManager(absBuilder),
        OnMAFApiManagerListener {
        override fun getInstance(): AbsMAFApiManager {
            return this
        }
    }

    private class AbsMAFMobileRequestImpl : AbsMAFMobileRequest()

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/22/022
     * @description  定义get请求
     * @param method 请求的整个方法名称
     * @param mapString 传递的参数
     * @param callBackSuccess 成功回调
     * @param callBackFail 失败回调
     * @param onDisposablesListener disposales
     */
    fun <T> get(
        method: String,
        urlMap: Map<String, Any>?,
        parseResultDataImpl: ParseResultDataImpl<T>,
        onDisposablesListener: OnDisposablesListener?
    ) {
        mAbsMAFMobileRequestImpl.get(method,urlMap,parseResultDataImpl,parseResultDataImpl,onDisposablesListener)
    }



    fun setOnRequestResultCallBackConfigListener(onRequestResultCallBackConfigListener: OnRequestResultCallBackConfigListener?) {
        mAbsMAFMobileRequestImpl.setOnRequestResultCallBackConfigListener(
            onRequestResultCallBackConfigListener
        )
    }


}