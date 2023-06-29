package com.gxx.networklibrary.networkpackge.apiservice.base.inter;

import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestFailListener;
import com.gxx.networklibrary.networkpackge.apiservice.OnRequestSuccessListener;
import com.gxx.networklibrary.networkpackge.customobserver.filedownobserver.FileDownLoadObserver;
import com.gxx.networklibrary.networkpackge.customobserver.fileupobserver.FileUploadObserver;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/24/024
 * @description  做请求封装接口
 **/
public interface OnDoRequestListener {
    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  做表单请求
     * @param method 整个接口名称，包含域名在内
     * @param urlMapString 拼接在url后面
     * @param  mapForm 传递到表单的参数
     * @param  isSync true 同步请求
     * @param  requestResultValue 希望服务器给的集合的结果
     * @param onRequestSuccessListener 成功接口回调
     * @param onRequestFailListener 失败接口回调
     * @param onDisposablesListener rxjava的Disposable
     **/
    void doPostFormRequest(String method, Map<String, Object> urlMapString, Map<String, Object> mapForm, boolean isSync, int requestResultValue, final OnRequestSuccessListener onRequestSuccessListener, final OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

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
    void doPostBodyRequest(String method,Map<String, Object> urlMapString, Map<String, Object> mapBody, boolean isSync, int requestResultValue, final OnRequestSuccessListener onRequestSuccessListener, final OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

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
    void doGetRequest(String method,Map<String, Object> urlMapString, boolean isSync, int requestResultValue, final OnRequestSuccessListener onRequestSuccessListener, final OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

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
    void doPutBodyRequest(String method,Map<String, Object> urlMapString, Map<String, Object> mapBody, boolean isSync, int requestResultValue, final OnRequestSuccessListener onRequestSuccessListener, final OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

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
   void doUploadFilesRequest(String method, Map<String, Object> urlMapString, boolean isSync, int requestResultValue, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  单文件上次，含有进度返回，同时可以上次body
     * @param  method  整个接口名称，包含域名在内
     * @param  keyStr 上传的文件的名称，服务器提供的名字
     * @param  file 上面的文件
     * @param mapRequestBody post到body的参数
     * @param fileUploadObserver 上传的进度
     **/
   void doUploadFileProgress(String method, String keyStr, File file, Map<String, RequestBody> mapRequestBody, final FileUploadObserver<ResponseBody> fileUploadObserver);


    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件下载到沙河目录
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
   void doDownloadFileProgress(String url, final String destDir, final String fileName, final FileDownLoadObserver<File> fileDownLoadObserver);

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件下载到沙河目录
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
   void doSyncDownloadFileProgress(String url, final String destDir, final String fileName, final FileDownLoadObserver<File> fileDownLoadObserver);
}
