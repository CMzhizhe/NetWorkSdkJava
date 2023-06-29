package com.gxx.networkproject.api

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/29/029
 * @description  自定义API
 **/
interface CustomApi {
    @GET
    fun getJsonRequest(
        @Url url: String,
        @QueryMap mapString: Map<String, @JvmSuppressWildcards Any>
    ): Observable<ResponseBody>
}