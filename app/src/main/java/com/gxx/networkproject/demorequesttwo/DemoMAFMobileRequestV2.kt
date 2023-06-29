package com.gxx.networkproject.demorequesttwo

import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener
import com.gxx.networklibrary.networkpackge.apiservice.base.AbsMAFApiManager
import com.gxx.networklibrary.networkpackge.apiservice.base.AbsMAFMobileRequest
import com.gxx.networklibrary.networkpackge.apiservice.base.BAFABSMAFMobileRequest
import com.gxx.networkproject.constant.Constant
import com.gxx.networkproject.demorequesttwo.factory.FactoryImpl
import com.gxx.networkproject.demorequesttwo.parse.ParseResultDataImpl
import com.gxx.networkproject.demorequesttwo.parse.ResponseTransform


/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/22/022
 * @description  TODO  当前类，调用者可以自行定义
 */
class DemoMAFMobileRequestV2 private constructor() {
    private var mAbsMAFApiManagerImpl: AbsMAFApiManagerImpl? = null
    private val mAbsMAFMobileRequestImpl: AbsMAFMobileRequestImpl = AbsMAFMobileRequestImpl()
    private val mResponseTransform = ResponseTransform()

    companion object {
        private var mDemoMAFMobileRequest: DemoMAFMobileRequestV2? = null
        fun getInstance(): DemoMAFMobileRequestV2 {
            if (mDemoMAFMobileRequest == null) {
                mDemoMAFMobileRequest = DemoMAFMobileRequestV2()
            }
            return mDemoMAFMobileRequest!!
        }
    }

    init {
        val builder = AbsMAFApiManager.Builder()
            .setCostTime(2 * 1000L) //设置方法请求超时接口回调
            .setRequestUrl(Constant.BASE_REQUEST_V2) //设置base Url
            .setRetryOnConnectionFailure(false) //是否重新连接
            .setIsDevmodel(true) //是否开发者模式，开发者模式，会打印网络请求日志
            .setOnFactoryListener(FactoryImpl())
            .setOnObservableSourceStringListener(mResponseTransform)
        mAbsMAFApiManagerImpl = AbsMAFApiManagerImpl(builder)
        mAbsMAFMobileRequestImpl.setOnMAFApiManagerListener(mAbsMAFApiManagerImpl)
    }

    private class AbsMAFApiManagerImpl(absBuilder: Builder) : AbsMAFApiManager(absBuilder),
        BAFABSMAFMobileRequest.OnMAFApiManagerListener {
        override fun getInstance(): AbsMAFApiManager {
            return this
        }
    }

    private class AbsMAFMobileRequestImpl : AbsMAFMobileRequest()

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  获取自定义的api
     **/
    fun <T> getApi(clazz: Class<T>):T{
        return mAbsMAFApiManagerImpl!!.getApi(clazz)
    }

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



    fun setOnRequestResultCallBackConfigListener(onRequestResultCallBackConfigListener: BAFABSMAFMobileRequest.OnRequestResultCallBackConfigListener?) {
        mAbsMAFMobileRequestImpl.setOnRequestResultCallBackConfigListener(
            onRequestResultCallBackConfigListener
        )
    }


}