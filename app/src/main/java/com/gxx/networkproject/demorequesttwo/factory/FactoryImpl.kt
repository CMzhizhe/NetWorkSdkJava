package com.gxx.networkproject.demorequesttwo.factory

import com.gxx.networklibrary.networkpackge.inter.OnFactoryListener
import com.gxx.networkproject.demorequest.interceptor.HeadInterceptor
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FactoryImpl: OnFactoryListener {
    override fun converterFactorys(): MutableList<Converter.Factory> {
        val list = mutableListOf<Converter.Factory>();
        list.add(GsonConverterFactory.create())
        return list
    }

    override fun callAdapterFactorys(): MutableList<CallAdapter.Factory> {
        val list = mutableListOf<CallAdapter.Factory>()
        list.add(RxJava3CallAdapterFactory.create())
        return list
    }

    override fun interceptors(): MutableList<Interceptor> {
        val list = mutableListOf<Interceptor>()
        list.add(HeadInterceptor())
        return list;
    }

    override fun netWorkInterceptors(): MutableList<Interceptor> {
         return mutableListOf()
    }
}