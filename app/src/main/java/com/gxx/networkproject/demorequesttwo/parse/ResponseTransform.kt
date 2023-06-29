package com.gxx.networkproject.demorequesttwo.parse


import com.google.gson.JsonParser
import com.gxx.networklibrary.networkpackge.exceptonhandle.ExceptionHandle
import com.gxx.networklibrary.networkpackge.transformer.OnObservableSourceStringListener
import com.gxx.networkproject.demorequesttwo.basemodel.BaseModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.functions.Function
import okhttp3.ResponseBody

/**
 * @date 创建时间: 2022/3/13
 * @auther gaoxiaoxiong
 * @description TODO  处理服务器给的结果，转换成基类，也就是开发者需要自己根据服务器的需求更改这里
 **/
class ResponseTransform : OnObservableSourceStringListener {
    companion object {
        const val RESPONSESTATUS_STATUS = "status"
        const val RESPONSESTATUS_DATA = "data"
        const val RESPONSESTATUS_MSG = "message"

        const val RESPONSESTATUS_STATUS_200 = "200"
    }

    /**
     * @date 创建时间: 2022/3/13
     * @auther gaoxiaoxiong
     * @description 将 ResponseBody 最终转为 String
     **/
    override fun onObservableSource(): ObservableTransformer<ResponseBody, String> {
        return ObservableTransformer<ResponseBody, String> { upstream ->
            upstream
                .onErrorResumeNext(ErrorJsonResumeFunction()) //处理服务器给的崩溃异常，或者是404异常  这里的404异常是在  retrofit BodyObservable 一个类里处理的 onNext 方法，他里面处理了 HttpException 异常
                .flatMap(ResponseJsonFunction())

        }
    }

    /**
     * @date 创建时间: 2022/3/14
     * @auther gaoxiaoxiong
     * @description 服务其返回的数据解析
     **/
    private class ResponseJsonFunction :
        Function<ResponseBody, Observable<String>> {
        @Throws(Exception::class)
        override fun apply(tResponse: ResponseBody): Observable<String> {
            val jsString = tResponse.string()
            if (JsonParser.parseString(jsString).isJsonObject) {//服务器提供的是jsonObject
                val jsonObject = JsonParser.parseString(jsString).asJsonObject
                //用户自己先在这里，将正确结果传递给  Observable.just
                return if (jsonObject.has(RESPONSESTATUS_STATUS)) { //正常的逻辑
                    val status = jsonObject[RESPONSESTATUS_STATUS].asString
                    if (status == RESPONSESTATUS_STATUS_200) {
                        Observable.just(jsonObject.toString())
                    } else {
                        //无法解析的时候，当 ServerException
                        val message = jsonObject[RESPONSESTATUS_MSG].asString
                        Observable.error(
                            ExceptionHandle.ServerException(
                                status,
                                message,
                                jsonObject.toString()
                            )
                        ) //直接进入BaseObserver 的onError
                    }
                } else { //非我们自己业务的逻辑
                    Observable.just(jsonObject.toString())
                }
            } else {
                return Observable.just(JsonParser.parseString(jsString).asJsonArray.toString())
            }
        }
    }

    /**
     * @date 创建时间: 2022/3/13
     * @auther gaoxiaoxiong
     * @description 转换status code等需要的信息
     * @param jsonString 拿到 ResponseJsonFunction 提供的数据
     * @param method 请求的接口名称
     **/
    override fun doParseJson2StatusCodeMore(
        method: String,
        jsonString: String?
    ): BaseModel {
        if (jsonString.isNullOrEmpty()) {
            return BaseModel(method, jsonString)
        }
        val baseBean = BaseModel()
        val resourceJsonElement = JsonParser.parseString(jsonString)
        val jsonObject = resourceJsonElement.asJsonObject
        baseBean.apply {
            message = jsonObject.get("message").asString
            nu = jsonObject.get("nu").asString
            ischeck = jsonObject.get("ischeck").asString
            com = jsonObject.get("com").asString
            status = jsonObject.get("status").asString
            condition = jsonObject.get("condition").asString
            state = jsonObject.get("state").asString

            this.method = method
            this.resourceJsonString = jsonString
            this.targetJsonElement =  jsonObject.get(RESPONSESTATUS_DATA)
        }
        return baseBean
    }


    /**
     * 服务器产生的异常，非正常状态的异常，比如404
     * @description:JSON解析
     */
    private class ErrorJsonResumeFunction :
        Function<Throwable, ObservableSource<out ResponseBody>> {
        @Throws(Exception::class)
        override fun apply(throwable: Throwable?): ObservableSource<out ResponseBody> {
            return Observable.error(ExceptionHandle.handleException(throwable))
        }
    }

}