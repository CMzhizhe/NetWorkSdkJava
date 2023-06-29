package com.gxx.networklibrary.networkpackge.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建时间: 2019/7/21
 * gxx
 * 注释描述:请求头的拦截添加。此模板仅仅作为参考
 */
public class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type","application/json;charset=UTF-8");
        return chain.proceed(builder.build());
    }

}
