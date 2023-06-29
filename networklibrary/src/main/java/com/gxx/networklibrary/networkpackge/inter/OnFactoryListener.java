package com.gxx.networklibrary.networkpackge.inter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

public interface OnFactoryListener {
    List<Converter.Factory> converterFactorys();

    List<CallAdapter.Factory> callAdapterFactorys();

    List<Interceptor> interceptors();

    List<Interceptor> netWorkInterceptors();
}
