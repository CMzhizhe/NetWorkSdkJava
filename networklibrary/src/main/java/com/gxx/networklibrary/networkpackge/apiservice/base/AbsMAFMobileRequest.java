package com.gxx.networklibrary.networkpackge.apiservice.base;

import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestFailListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestSuccessListener;
import com.gxx.networklibrary.networkpackge.apiservice.base.inter.ABSMAFMobileRequestListener;
import com.gxx.networklibrary.networkpackge.customobserver.filedownobserver.FileDownLoadObserver;
import com.gxx.networklibrary.networkpackge.customobserver.fileupobserver.FileUploadObserver;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @date: 2019/7/19 0019
 * @author: gaoxiaoxiong
 * @description:封装一些常用的请求方式
 **/
public  class AbsMAFMobileRequest extends BAFABSMAFMobileRequest implements ABSMAFMobileRequestListener {


    /**
     * @date 创建时间:2020/5/25 0025
     * @auther gaoxiaoxiong
     * @Descriptiion 上传错误信息到服务器
     **/
    @Override
    protected void postErrorToService(String method, Exception throwable) {
        throwable.printStackTrace();
        if (weakReferenceArrayList != null && !weakReferenceArrayList.isEmpty()) {
            for (WeakReference<OnRequestResultCallBackConfigListener> onRequestResultCallBackConfigListenerWeakReference : weakReferenceArrayList) {
                if (onRequestResultCallBackConfigListenerWeakReference != null && onRequestResultCallBackConfigListenerWeakReference.get() != null) {
                    onRequestResultCallBackConfigListenerWeakReference.get().onRequestResultCallBackError(method, throwable);
                }
            }
        }
    }

    @Override
    public void get(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(false,REQUEST_RESULT_OWN,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void getResObj(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(false,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void getResList(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(false,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void getSync(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(true,REQUEST_RESULT_OWN,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void getSyncResObj(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(true,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void getSyncResList(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.get(true,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postForm(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postForm(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFormResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFormResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFormResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFormResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(false,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncForm(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncForm(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFormResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFormResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFormResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFormResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postForm(true,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBody(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBodyResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postBodyResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(false,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBody(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBodyResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncBodyResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postBody(true,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBody(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBodyResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putBodyResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(false,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_OWN,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBody(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_OWN,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_OBJECT,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBodyResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_ARRAY,requestUrl,null,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void putSyncBodyResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.putBody(true,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,mapString,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFiles(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
       this.postFiles(false,REQUEST_RESULT_OWN,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFilesResObj(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(false,REQUEST_RESULT_OBJECT,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFilesResList(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(false,REQUEST_RESULT_ARRAY,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFiles(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(false,REQUEST_RESULT_OWN,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFilesResObj(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(false,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postFilesResList(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(false,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFiles(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_OWN,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFilesResObj(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_OBJECT,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFilesResList(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_ARRAY,requestUrl,null,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFiles(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_OWN,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFilesResObj(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_OBJECT,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    @Override
    public void postSyncFilesResList(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener) {
        this.postFiles(true,REQUEST_RESULT_ARRAY,requestUrl,urlMapString,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
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
    public void uploadSingleFileProgress(String method, String keyStr, File file, Map<String, RequestBody> mapRequestBody, FileUploadObserver<ResponseBody> fileUploadObserver) {
        this.doUploadFileProgress(method, keyStr, file, mapRequestBody, fileUploadObserver);
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
    public void downFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver) {
        this.doDownloadFileProgress(url, destDir, fileName, fileDownLoadObserver);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件下载，同步
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
    @Override
    public void downSyncFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver) {
        this.doDownloadFileProgress(url, destDir, fileName, fileDownLoadObserver);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description get 请求
     * @param isSync 是否同步
     * @param resultValue 期望的结果，1_自己处理 2_只返回对象 3_只返回数组
     * @param requestUrl 请求的链接，包含接口名称一起
     * @param urlMapString 拼接在接口后面的参数
     * @param onRequestSuccessListener 成功回调
     * @param onRequestFailListener 失败回调
     * @param onDisposablesListener Disposable
     **/
    private void get(boolean isSync,int resultValue,String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener){
        this.doGetRequest(requestUrl,urlMapString,isSync,resultValue,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description post 请求 表单请求
     * @param isSync 是否同步
     * @param resultValue 期望的结果，1_自己处理 2_只返回对象 3_只返回数组
     * @param requestUrl 请求的链接，包含接口名称一起
     * @param urlMapString 拼接在接口后面的参数
     * @param onRequestSuccessListener 成功回调
     * @param onRequestFailListener 失败回调
     * @param onDisposablesListener Disposable
     **/
    private void postForm(boolean isSync,int resultValue,String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener){
        this.doPostFormRequest(requestUrl,urlMapString,mapString,isSync,resultValue,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description post 请求 body
     * @param isSync 是否同步
     * @param resultValue 期望的结果，1_自己处理 2_只返回对象 3_只返回数组
     * @param requestUrl 请求的链接，包含接口名称一起
     * @param urlMapString 拼接在接口后面的参数
     * @param onRequestSuccessListener 成功回调
     * @param onRequestFailListener 失败回调
     * @param onDisposablesListener Disposable
     **/
    private void postBody(boolean isSync,int resultValue,String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener){
        this.doPostBodyRequest(requestUrl,urlMapString,mapString,isSync,resultValue,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description put 请求 body
     * @param isSync 是否同步
     * @param resultValue 期望的结果，1_自己处理 2_只返回对象 3_只返回数组
     * @param requestUrl 请求的链接，包含接口名称一起
     * @param urlMapString 拼接在接口后面的参数
     * @param onRequestSuccessListener 成功回调
     * @param onRequestFailListener 失败回调
     * @param onDisposablesListener Disposable
     **/
    private void putBody(boolean isSync,int resultValue,String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener){
        this.doPutBodyRequest(requestUrl,urlMapString,mapString,isSync,resultValue,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/25/025
     * @description  文件上传集合
     * @param isSync 是否同步
     * @param resultValue 期望的结果，1_自己处理 2_只返回对象 3_只返回数组
     * @param requestUrl 请求的链接，包含接口名称一起
     * @param urlMapString 拼接在接口后面的参数
     * @param onRequestSuccessListener 成功回调
     * @param onRequestFailListener 失败回调
     * @param onDisposablesListener Disposable
     **/
    private void postFiles(boolean isSync,int resultValue,String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener){
        this.doUploadFilesRequest(requestUrl,urlMapString,isSync,resultValue,partList,mapRequestBody,onRequestSuccessListener,onRequestFailListener,onDisposablesListener);
    }
}
