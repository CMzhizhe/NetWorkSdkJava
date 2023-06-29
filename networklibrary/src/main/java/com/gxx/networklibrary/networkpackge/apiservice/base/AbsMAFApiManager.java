package com.gxx.networklibrary.networkpackge.apiservice.base;


import com.gxx.networklibrary.networkpackge.apiservice.MAFApiService;
import com.gxx.networklibrary.networkpackge.inter.OnFactoryListener;
import com.gxx.networklibrary.networkpackge.transformer.OnObservableSourceStringListener;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public abstract class AbsMAFApiManager {
    private Retrofit retrofit = null;
    private String requestUrl = null;
    private boolean retryOnConnectionFailure = false;
    private boolean isDevmodel = false;
    private long costTime = 2 * 1000;
    private OnObservableSourceStringListener onObservableSourceStringListener = null;
    private OnFactoryListener onFactoryListener = null;
    public int connectTimeoutSecond = 10;
    public int readTimeout = 10;

    public AbsMAFApiManager(Builder absBuilder) {
        super();
        this.requestUrl = absBuilder.requestUrl;
        this.retryOnConnectionFailure = absBuilder.retryOnConnectionFailure;
        this.isDevmodel = absBuilder.isDevmodel;
        this.onObservableSourceStringListener = absBuilder.onObservableSourceStringListener;
        this.onFactoryListener = absBuilder.onFactoryListener;
        this.connectTimeoutSecond = absBuilder.connectTimeoutSecond;
        this.readTimeout = absBuilder.readTimeout;

        if (this.onObservableSourceStringListener == null) {
            throw new IllegalStateException("onObservableSourceStringListener 为null，详情使用请看 RequestObservableSourceImpl");
        }

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();//日志太多会崩溃
        if (isDevmodel) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeoutSecond, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(retryOnConnectionFailure);     //是否失败重新请求连接

        if (onFactoryListener != null ) {
            if (onFactoryListener.interceptors()!=null){
                for (Interceptor onInterceptor : onFactoryListener.interceptors()) {
                    okBuilder.addInterceptor(onInterceptor);
                }
            }

            if (onFactoryListener.netWorkInterceptors()!=null){
                for (Interceptor netWorkInterceptor : onFactoryListener.netWorkInterceptors()) {
                    okBuilder.addNetworkInterceptor(netWorkInterceptor);
                }
            }
        }

        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl(this.requestUrl)
                .client(okBuilder.build());

        if (onFactoryListener != null) {
            if (onFactoryListener.callAdapterFactorys() != null && !onFactoryListener.callAdapterFactorys().isEmpty()) {
                for (CallAdapter.Factory callAdapterFactory : onFactoryListener.callAdapterFactorys()) {
                    reBuilder.addCallAdapterFactory(callAdapterFactory);
                }
            }

            if (onFactoryListener.converterFactorys() != null && !onFactoryListener.converterFactorys().isEmpty()) {
                for (Converter.Factory converterFactory : onFactoryListener.converterFactorys()) {
                    reBuilder.addConverterFactory(converterFactory);
                }
            }
        }
        retrofit = reBuilder.build();
    }

    public OnObservableSourceStringListener getOnObservableSourceStringListener() {
        return onObservableSourceStringListener;
    }

    public long getCostTime() {
        return costTime;
    }

    /**
     * @date: 2019/3/8 0008
     * @author: gaoxiaoxiong
     * @description:获取接口
     **/
    public MAFApiService createBaseApi() {
        return getApi(MAFApiService.class);
    }

    /**
     * 获取接口
     * 这里返回的接口的基类，需要自行把强制转换为需要返回的接口
     * @param clazz 类类型
     * @date 2018-06-11 16:24
     */
    public <T> T getApi(Class<T> clazz) {
        return (T) retrofit.create(clazz);
    }

    public static class Builder {
        public int connectTimeoutSecond = 10;
        public int readTimeout = 10;
        public String requestUrl = null;
        public boolean retryOnConnectionFailure = false;
        public boolean isDevmodel = false;
        public long costTime = 2 * 1000;
        public OnObservableSourceStringListener onObservableSourceStringListener;
        public OnFactoryListener onFactoryListener;

        public Builder setConnectTimeoutSecond(int timeoutSecond){
            if (timeoutSecond<=0){
                timeoutSecond = 10;
            }
            this.connectTimeoutSecond = timeoutSecond;
            return this;
        }

        public Builder setReadTimeout(int timeout){
            if (timeout<=0){
                timeout = 10;
            }
            this.readTimeout = timeout;
            return this;
        }

        /**
         * @date 创建时间:2022/3/22 0022
         * @auther gaoxiaoxiong
         * @Descriptiion 返回Factory
         **/
        public Builder setOnFactoryListener(OnFactoryListener onFactoryListener) {
            this.onFactoryListener = onFactoryListener;
            return this;
        }

        /**
         * @date 创建时间: 2022/3/13
         * @auther gaoxiaoxiong
         * @description 方法耗时通知
         **/
        public Builder setCostTime(long costTime) {
            this.costTime = costTime;
            return this;
        }

        /**
         * @date 创建时间: 2022/3/13
         * @auther gaoxiaoxiong
         * @description 设置转换类型接口监听，不可以为null
         **/
        public Builder setOnObservableSourceStringListener(OnObservableSourceStringListener onObservableSourceStringListener) {
            this.onObservableSourceStringListener = onObservableSourceStringListener;
            return this;
        }

        /**
         * @date 创建时间: 2022/3/13
         * @auther gaoxiaoxiong
         * @description 是否开发者模式，true，是的，开发者模式会打印日志
         **/
        public Builder setIsDevmodel(boolean devmodel) {
            this.isDevmodel = devmodel;
            return this;
        }

        /**
         * @date 创建时间: 2022/3/12
         * @auther gaoxiaoxiong
         * @description 设置网络请求地址
         **/
        public Builder setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        /**
         * @date 创建时间: 2022/3/12
         * @auther gaoxiaoxiong
         * @description 是否开启重试
         **/
        public Builder setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }


    }
}
