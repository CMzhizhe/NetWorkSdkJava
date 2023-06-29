package com.gxx.networklibrary.networkpackge.apiservice;


import java.util.List;
import java.util.Map;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

//https://www.jianshu.com/p/be4007f8eac7
//https://www.jianshu.com/p/308f3c54abdd/
//https://blog.csdn.net/u013449800/article/details/77772656 传递对象数组
//https://blog.csdn.net/yuzhangzhen/article/details/109958977 【Retrofit】Retrofit原理解析之注解详解篇
//@Body标签不能和@FormUrlEncoded标签同时使用
public interface MAFApiService {

    /**
     * http://www.5mins-sun.com:8081/news?name=admin&pwd=123456
     * @param mapString map里面的Object 只能是String ，int
     **/
    @GET()
    Observable<ResponseBody> getJsonRequest(@Url String url, @QueryMap Map<String, Object> mapString);

    /**
     * RequestBody:
     * {
     * "albumID": 2,
     * "sectionID": 16
     * }
     **/
    @POST()
    Observable<ResponseBody> postJsonBodyRequest(@Url String url,@QueryMap Map<String,Object> map, @Body RequestBody body);

    /**
     * Post请求, 以表单方式提交
     * user=admin
     * pwd=123456
     * @param mapString map里面的Object 只能是String ，int
     **/
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postFormRequest(@Url String ur,@QueryMap Map<String,Object> queryMap, @FieldMap Map<String, Object> mapString);


    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/24/024
     * @description 上次，文件、Body、Url后面拼接参数
     **/
    @Multipart
    @POST()
    Observable<ResponseBody> postFilesBodysUrlsRequest(@Url String url, @Part List<MultipartBody.Part> files, @PartMap Map<String, RequestBody> mapRequestBody, @QueryMap Map<String, Object> mapString);

    /**
     * @param file       存放在body里面
     * @param mapRequestBody 存放在body里面
     * @date: 2019/7/20 0020
     * @author: gaoxiaoxiong
     * @description:上传单个图片&一些参数
     **/
    @Multipart
    @POST()
    Observable<ResponseBody> postSingleFileBodysUrlsRequest(@Url String url, @Part MultipartBody.Part file, @PartMap Map<String, RequestBody> mapRequestBody, @QueryMap Map<String, Object> mapString);


    /**
     * 作者：GaoXiaoXiong
     * 创建时间:2019/7/21
     * 注释描述:文件上传
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part file, @PartMap Map<String, RequestBody> mapRequestBody);

    /**
     * @date: 2019/7/20 0020
     * @author: gaoxiaoxiong
     * RequestBody:
     * {
     * "albumID": 2,
     * "sectionID": 16
     * }
     **/
    @PUT()
    Observable<ResponseBody> putJsonBodyRequest(@Url String url,@QueryMap Map<String,Object> map, @Body RequestBody body);


}
