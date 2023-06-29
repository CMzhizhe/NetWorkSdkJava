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

public interface ABSMAFMobileRequestListener {
    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description get 请求，请求结果自行处理
     * @param  requestUrl 请求的连接 + 接口名称
     * @param urlMapString 拼接在url后面参数
     * @param onRequestFailListener 请求失败
     * @param onRequestSuccessListener 请求成功
     * @param onDisposablesListener Disposable
     **/
    void get(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);
    //get 请求，请求结果只返回对象，不是对象的 当错误处理
    void getResObj(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);
    //get 请求，请求结果只返回List，不是List的 当错误处理
    void getResList(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    //get 请求，同步请求，自行开线层，不会帮你主动开，请求结果自行处理
    void getSync(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void getSyncResObj(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void getSyncResList(String requestUrl, Map<String, Object> urlMapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postForm(String requestUrl,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postForm(String requestUrl, Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFormResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFormResObj(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFormResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFormResList(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncForm(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncForm(String requestUrl, Map<String, Object> urlMapString,Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFormResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFormResObj(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFormResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFormResList(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBody(String requestUrl,Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBodyResObj(String requestUrl,Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postBodyResList(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBody(String requestUrl,Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBodyResObj(String requestUrl,Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncBodyResList(String requestUrl,Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBody(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBodyResObj(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putBodyResList(String requestUrl, Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBody(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBody(String requestUrl,  Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBodyResObj(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBodyResObj(String requestUrl,Map<String, Object> urlMapString,  Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBodyResList(String requestUrl, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void putSyncBodyResList(String requestUrl, Map<String, Object> urlMapString, Map<String, Object> mapString, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    //文件上传，多个
    void postFiles(String method, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    //文件上传，多个
    void postFilesResObj(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFilesResList(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    //文件上传，多个
    void postFiles(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFilesResObj(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postFilesResList(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);


    //文件上传，多个
    void postSyncFiles(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFilesResObj(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFilesResList(String requestUrl, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    //文件上传，多个
    void postSyncFiles(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFilesResObj(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);

    void postSyncFilesResList(String requestUrl, Map<String, Object> urlMapString, List<MultipartBody.Part> partList, Map<String, RequestBody> mapRequestBody, OnRequestSuccessListener onRequestSuccessListener, OnRequestFailListener onRequestFailListener, OnDisposablesListener onDisposablesListener);


    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  单文件上次，含有进度返回，同时可以上次body
     * @param  method  整个接口名称，包含域名在内
     * @param  keyStr 上传的文件的名称，服务器提供的名字
     * @param  file 上面的文件
     * @param mapRequestBody post到body的参数
     * @param fileUploadObserver 上传的进度
     *                           上传文件含有进度条
     * https://blog.csdn.net/wwj_748/article/details/77840641#%E5%AE%9A%E4%B9%89%E4%B8%8A%E4%BC%A0%E6%96%87%E4%BB%B6%E6%8E%A5%E5%8F%A3
     **/
    void uploadSingleFileProgress(String method, String keyStr, File file, Map<String, RequestBody> mapRequestBody, FileUploadObserver<ResponseBody> fileUploadObserver);

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  文件下载
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
    void downFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver);

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description  同步文件下载
     * @param url 地址
     * @param destDir 下载的目标目录
     * @param fileName 下载的文件的名称
     * @param fileDownLoadObserver 下载进度
     **/
    void downSyncFileProgress(String url, String destDir, String fileName, FileDownLoadObserver<File> fileDownLoadObserver);
}
