package com.gxx.networklibrary.networkpackge.apiservice.base;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestFailListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestSuccessListener;
import com.gxx.networklibrary.networkpackge.apiservice.base.inter.OnDoRequestListener;
import com.gxx.networklibrary.networkpackge.customobserver.BaseObserver;
import com.gxx.networklibrary.networkpackge.customobserver.filedownobserver.FileDownLoadObserver;
import com.gxx.networklibrary.networkpackge.customobserver.fileupobserver.FileUploadObserver;
import com.gxx.networklibrary.networkpackge.customobserver.fileupobserver.UploadFileRequestBody;
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener;
import com.gxx.networklibrary.networkpackge.requestencapsulutil.MultipartBodyUtils;
import com.gxx.networklibrary.networkpackge.schedulers.SchedulerProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public abstract class BAFABSMAFMobileRequest implements  OnDoRequestListener {
    private final String TAG = "ABSMAFMobileRequest";
    private Gson mGson = new Gson();

    protected final int REQUEST_RESULT_OWN = 1;//自己处理类型
    protected final int REQUEST_RESULT_OBJECT = 2;//只返回对象
    protected final int REQUEST_RESULT_ARRAY = 3;//只返回数组
    protected OnMAFApiManagerListener mOnMAFApiManagerListener = null;
    protected List<WeakReference<OnRequestResultCallBackConfigListener>> weakReferenceArrayList = new ArrayList<>();

    public void setOnMAFApiManagerListener(OnMAFApiManagerListener onMAFApiManagerListener) {
        this.mOnMAFApiManagerListener = onMAFApiManagerListener;
    }

    public void setOnRequestResultCallBackConfigListener(OnRequestResultCallBackConfigListener onRequestResultCallBackConfigListener) {
        weakReferenceArrayList.add(new WeakReference<OnRequestResultCallBackConfigListener>(onRequestResultCallBackConfigListener));
    }


    public interface OnMAFApiManagerListener {
        AbsMAFApiManager getInstance();
    }

    public interface OnRequestResultCallBackConfigListener {

        /**
         * @param interfaceName 接口名称
         * @param throwable     异常
         * @date 创建时间:2020/7/29 0029
         * @auther gaoxiaoxiong
         * @Descriptiion 异常接口回调
         **/
        void onRequestResultCallBackError(String interfaceName, Throwable throwable);


        /**
         * @param interfaceName 接口名称
         * @param costTimeArray 耗时集合 0开始的时间  1接收到服务器后的时间  2处理完结果后的时间
         * @date 创建时间:2021/10/27 0027
         * @auther gaoxiaoxiong
         * @Descriptiion 耗时操作回调
         **/
        void onRequestCostTimeCallBack(String interfaceName, Long[] costTimeArray);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  做Body请求
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  mapBody 传递到body里面的参数
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    @Override
    public void doPutBodyRequest(String method, Map<String, Object> urlMapString, Map<String, Object> mapBody, boolean isSync, int requestResultValue, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        if (mapBody == null || mapBody.isEmpty()){
            Log.e(TAG, "mapBody 是null");
            return;
        }

        final Long[] costTimeArray = new Long[2];
        costTimeArray[0] = System.currentTimeMillis();

        Observable<ResponseBody> responseBodyObservable = null;

        RequestBody requestBody = MultipartBodyUtils.getInstance().jsonToRequestBody(mapToJson(mapBody));

        if (urlMapString == null){
            urlMapString = new HashMap<>(2);
        }

        responseBodyObservable = mOnMAFApiManagerListener.getInstance().createBaseApi().putJsonBodyRequest(method,urlMapString,requestBody);

        if (responseBodyObservable == null){
            Log.e(TAG, "responseBodyObservable 是null");
            return;
        }

        if (isSync) {
            doSyncComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        } else {
            doComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        }
    }


    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  做表单请求
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  mapString 传递到表单的参数
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    @Override
    public void doPostFormRequest(String method, Map<String, Object> urlMapString, Map<String, Object> mapString, boolean isSync, int requestResultValue, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        if (mapString == null || mapString.isEmpty()){
            Log.e(TAG, "请求到表单的参数 是null");
            return;
        }

        final Long[] costTimeArray = new Long[2];
        costTimeArray[0] = System.currentTimeMillis();

        Observable<ResponseBody> responseBodyObservable = null;

        if (urlMapString == null){
            urlMapString = new HashMap<>(2);
        }

        responseBodyObservable = mOnMAFApiManagerListener.getInstance().createBaseApi().postFormRequest(method,urlMapString,mapString);

        if (responseBodyObservable == null){
            Log.e(TAG, "responseBodyObservable 是null");
            return;
        }

        if (isSync) {
            doSyncComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        } else {
            doComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        }
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  做Body请求
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  mapBody 传递到body里面的参数
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    @Override
    public void doPostBodyRequest(String method, Map<String, Object> urlMapString, Map<String, Object> mapBody, boolean isSync, int requestResultValue, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        if (mapBody == null || mapBody.isEmpty()){
            Log.e(TAG, "mapBody 是null");
            return;
        }

        final Long[] costTimeArray = new Long[2];
        costTimeArray[0] = System.currentTimeMillis();

        Observable<ResponseBody> responseBodyObservable = null;

        RequestBody requestBody = MultipartBodyUtils.getInstance().jsonToRequestBody(mapToJson(mapBody));

        if (urlMapString == null){
            urlMapString = new HashMap<>(2);
        }

        responseBodyObservable = mOnMAFApiManagerListener.getInstance().createBaseApi().postJsonBodyRequest(method,urlMapString,requestBody);

        if (responseBodyObservable == null){
            Log.e(TAG, "responseBodyObservable 是null");
            return;
        }

        if (isSync) {
            doSyncComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        } else {
            doComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        }
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  做Body请求
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    @Override
    public void doGetRequest(String method, Map<String, Object> urlMapString, boolean isSync, int requestResultValue, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        final Long[] costTimeArray = new Long[2];
        costTimeArray[0] = System.currentTimeMillis();

        Observable<ResponseBody> responseBodyObservable = null;

        if (urlMapString == null){
            urlMapString = new HashMap<>(2);
        }

        responseBodyObservable = mOnMAFApiManagerListener.getInstance().createBaseApi().getJsonRequest(method,urlMapString);

        if (responseBodyObservable == null){
            Log.e(TAG, "responseBodyObservable == null");
            return;
        }

        if (isSync) {
            doSyncComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        } else {
            doComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        }
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件上传操作
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param partList 文件集合
     * @param mapRequestBody post到body的参数
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    @Override
    public void doUploadFilesRequest(String method, Map<String, Object> urlMapString, boolean isSync, int requestResultValue, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (partList == null) {
            throw new IllegalStateException("partList 不可以为null");
        }

        if (partList.isEmpty()){
            Log.e(TAG, "partList 是空的");
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        if (mapRequestBody == null){
            mapRequestBody = new HashMap<>(2);
        }

        if (urlMapString == null){
            urlMapString = new HashMap<>(2);
        }

        final Long[] costTimeArray = new Long[2];
        costTimeArray[0] = System.currentTimeMillis();

        Observable<ResponseBody> responseBodyObservable = null;
        if (partList.size() == 1){
            responseBodyObservable =  mOnMAFApiManagerListener.getInstance().createBaseApi().postSingleFileBodysUrlsRequest(method,partList.get(0),mapRequestBody,urlMapString);
        }else {
            responseBodyObservable = mOnMAFApiManagerListener.getInstance().createBaseApi().postFilesBodysUrlsRequest(method, partList, mapRequestBody, urlMapString);
        }

        if (responseBodyObservable == null){
            Log.e(TAG, "responseBodyObservable 是空的");
            return;
        }

        if (isSync) {
            doSyncComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        } else {
            doComposeMapRequest(method, responseBodyObservable, requestResultValue,costTimeArray, onRequestSuccessListener, onRequestFailListener, onDisposablesListener);
        }
    }


    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/22/022
     * @description 处理异步， 处理Observable<ResponseBody> 转换 && 线层切换
     **/
    private void doComposeMapRequest(String method, Observable<ResponseBody> responseBodyObservable, int requestResultValue, Long[] costTimeArray, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }

        responseBodyObservable.compose(mOnMAFApiManagerListener.getInstance().getOnObservableSourceStringListener().onObservableSource())
                .map(new Function<String, OnIParserListener>() {
                    @Override
                    public OnIParserListener apply(@NonNull String jsonString) {
                        return mOnMAFApiManagerListener.getInstance().getOnObservableSourceStringListener().doParseJson2StatusCodeMore(method, jsonString);
                    }
                })
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new BaseObserver<OnIParserListener>() {
                    @Override
                    public void onDisposable(io.reactivex.rxjava3.disposables.Disposable disposable) {
                        if (onDisposablesListener != null) {
                            onDisposablesListener.onDisposable(disposable);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable,String status, String msg, String errorJsonString) {
                        if (onRequestFailListener != null) {
                            onRequestFailListener.onRequestFail(throwable,status, msg, errorJsonString, null);
                        }
                    }

                    @Override
                    public void onNext(OnIParserListener onIParserListener) {
                        if (onRequestSuccessListener != null) {
                            doResultSuccess(onIParserListener, method, requestResultValue, onRequestSuccessListener, onRequestFailListener, costTimeArray);
                        }
                    }
                });
    }

    /**
     * 同步请求
     * @param requestResultValue 希望最后结果返回的类型，如果最后结果不是对应的，直接返回null
     * @date 创建时间:2020/12/28 0028
     * @auther gaoxiaoxiong
     * @Descriptiion 请求
     **/
    private void doSyncComposeMapRequest(String method, Observable<ResponseBody> responseBodyObservable, int requestResultValue, Long[] costTimeArray, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        if (TextUtils.isEmpty(method)) {
            return;
        }

        if (mOnMAFApiManagerListener == null) {
            Log.e(TAG, "onMAFApiManagerListener 是null");
            return;
        }
        responseBodyObservable
                .compose(mOnMAFApiManagerListener.getInstance().getOnObservableSourceStringListener().onObservableSource())
                .map(new Function<String, OnIParserListener>() {
                    @Override
                    public OnIParserListener apply(@NonNull String jsonString) {
                        return mOnMAFApiManagerListener.getInstance().getOnObservableSourceStringListener().doParseJson2StatusCodeMore(method, jsonString);
                    }
                })
                .blockingSubscribe(new BaseObserver<OnIParserListener>() {
                    @Override
                    public void onDisposable(Disposable disposable) {
                        if (onDisposablesListener != null) {
                            onDisposablesListener.onDisposable(disposable);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable,String status, String msg, String errorJsonString) {
                        if (onRequestFailListener != null) {
                            onRequestFailListener.onRequestFail(throwable,status, msg, errorJsonString, null);
                        }
                    }

                    @Override
                    public void onNext(@NonNull OnIParserListener onIParserListener) {
                        if (onRequestSuccessListener != null) {
                            doResultSuccess(onIParserListener, method, requestResultValue, onRequestSuccessListener, onRequestFailListener, costTimeArray);
                        }
                    }
                });
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件下载
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
    @Override
    public void doDownloadFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver) {
        Observable.just(url)
                .observeOn(Schedulers.newThread())
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String reqeustUrl) throws Exception {
                        return fileDownLoadObserver.saveFileToLocal(reqeustUrl, destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileDownLoadObserver);
    }

    @Override
    public void doSyncDownloadFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver) {
        Observable.just(url)
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String reqeustUrl) throws Exception {
                        return fileDownLoadObserver.saveFileToLocal(reqeustUrl, destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .blockingSubscribe(fileDownLoadObserver);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  单文件上次，含有进度返回，同时可以上次body
     * @param  method  整个接口名称，包含域名在内
     * @param  keyStr 上传的文件的名称，服务器提供的名字
     * @param  file 上面的文件
     * @param mapRequestBody post到body的参数
     * @param fileUploadObserver 上传的进度
     * https://blog.csdn.net/wwj_748/article/details/77840641#%E5%AE%9A%E4%B9%89%E4%B8%8A%E4%BC%A0%E6%96%87%E4%BB%B6%E6%8E%A5%E5%8F%A3
     **/
    @Override
    public void doUploadFileProgress(String method, String keyStr, File file, Map<String, RequestBody> mapRequestBody, FileUploadObserver<ResponseBody> fileUploadObserver) {
        if (mOnMAFApiManagerListener == null){
            return;
        }
        if (mapRequestBody == null) {
            mapRequestBody = new HashMap<>(2);
        }
        UploadFileRequestBody<ResponseBody> uploadFileRequestBody = new UploadFileRequestBody<>(file, fileUploadObserver);
        MultipartBody.Part partFile = MultipartBodyUtils.getInstance().createFileFormData(keyStr, file, uploadFileRequestBody);
        mOnMAFApiManagerListener.getInstance().createBaseApi().uploadFile(method, partFile, mapRequestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver);
    }

    /**
     * @date 创建时间:2021/1/4 0004
     * @auther gaoxiaoxiong
     * @Descriptiion 最后的结果处理
     **/
    private void doResultSuccess(final OnIParserListener onIParserListener, final String method, final int requestResultValue, final OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, final Long[] costTimeArray) {
        try {
            if (onRequestSuccessListener != null && onIParserListener != null) {
                if (requestResultValue == REQUEST_RESULT_OWN) {
                    onRequestSuccessListener.onRequestSuccess(method,onIParserListener.onTargetJsonElement(),onIParserListener);
                } else {
                    if (onIParserListener.isSuccess()){//成功
                        if (onIParserListener.onTargetJsonElement()== null) {
                            onRequestSuccessListener.onRequestSuccess(method,null,onIParserListener);
                        } else {
                            if (requestResultValue == REQUEST_RESULT_OBJECT) {
                                if (onIParserListener.onTargetJsonElement() instanceof JsonObject) {
                                    onRequestSuccessListener.onRequestSuccess(method, (JsonObject)onIParserListener.onTargetJsonElement(),onIParserListener );
                                } else {
                                    onRequestFailListener.onRequestFail(null,null,null,null,onIParserListener);
                                }
                            } else if (requestResultValue == REQUEST_RESULT_ARRAY) {
                                if (onIParserListener.onTargetJsonElement() instanceof JsonArray) {
                                    onRequestSuccessListener.onRequestSuccess(method, (JsonArray)onIParserListener.onTargetJsonElement(),onIParserListener);
                                } else {
                                    onRequestFailListener.onRequestFail(null,null,null,null,onIParserListener);
                                }
                            }
                        }
                    }else {//失败
                        onRequestFailListener.onRequestFail(null,null,null,null,onIParserListener);
                    }
                }
            }

            //处理超时现象
            if (costTimeArray != null && costTimeArray.length == 2 && weakReferenceArrayList != null && !weakReferenceArrayList.isEmpty()) {
                costTimeArray[1] = System.currentTimeMillis();
                if (costTimeArray[1] - costTimeArray[0] > mOnMAFApiManagerListener.getInstance().getCostTime()) {//服务器超过1秒 或者业务逻辑请求超过2秒
                    for (WeakReference<OnRequestResultCallBackConfigListener> onRequestResultCallBackConfigListenerWeakReference : weakReferenceArrayList) {
                        if (onRequestResultCallBackConfigListenerWeakReference != null && onRequestResultCallBackConfigListenerWeakReference.get() != null) {
                            onRequestResultCallBackConfigListenerWeakReference.get().onRequestCostTimeCallBack(method, costTimeArray);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            postErrorToService(method, exception);
        }
    }



    /**
     * @date 创建时间:2021/10/27 0027
     * @auther gaoxiaoxiong
     * @Descriptiion 上传错误信息到服务器
     **/
    protected abstract void postErrorToService(String method, Exception exception);

    /**
     * @date 创建时间: 2022/3/12
     * @auther gaoxiaoxiong
     * @description map转Json
     **/
    protected String mapToJson(Map<String, Object> map) {
        return mGson.toJson(map);
    }

}
