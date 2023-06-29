#### 目的
分享这个库的目的也是为了减少开发者的时间
#### 我做了什么
* 1、简化&&封装get、post、put、没有封装delete没用到过所以没写...
* 2、含有同步（需要自己开线层哦）异步请求调用
* 3、针对服务器返回的结果做了些特殊处理，比如调用者希望服务器返回对象的结构，但是服务器非要给数组，那么就会转换成失败调用，为啥要这样设计，因为世界上有最好的语言（哭）
* 4、添加intercept
* 5、构建多个域名的请求
* 6、自定义请求的API
* 7、自定义BaseBean
* 8、单文件上传、下载（带进度）
* 9、在onSuccess和onFail 方法里面崩溃回调
* 10、网络超时通知

[github，觉得不错，来个点赞](https://github.com/CMzhizhe/NetWorkSdkJava)

#### 下一步计划干嘛
准备弄个携程网络版本的封装

#### 简单示例
```
 /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  快递查询
     * @param type 快递类型 快递公司编码:申通="shentong" EMS="ems" 顺丰="shunfeng" 圆通="yuantong" 中通="zhongtong" 韵达="yunda" 天天="tiantian" 汇通="huitongkuaidi" 全峰="quanfengkuaidi" 德邦="debangwuliu" 宅急送="zhaijisong"
     * @param posId 快递单号
     **/
    fun readkuaidi(type: String, posId: String) {
        val requestUrl = Constant.getBaseRequestV2("query")//域名 + 接口
        val map = mutableMapOf<String,String>()//服务器需要的数据
        map["type"] = type;
        map["postid"] = posId; 
      //发起异步请求(请求地址，服务器传递参数，想要目标结果类型，disposables)
        DemoMAFMobileRequestV2.getInstance().get(requestUrl, map, object :
            com.gxx.networkproject.demorequesttwo.parse.ParseResultDataImpl<MutableList<KusiDiModel>>() {
            override fun onRequestBaseBeanSuccess(
                data: MutableList<KusiDiModel>?,
                baseBean: BaseModel
            ) {
                super.onRequestBaseBeanSuccess(data, baseBean)
                //throw IllegalStateException("来个故意崩溃，测试有捕获到错误，不会导致APP闪退出去")
            }

            override fun onRequestDataFail(code: String, msg: String, baseBean: BaseModel?) {
                super.onRequestDataFail(code, msg, baseBean)
            }
        }, this)
    }
```

#### 使用教程
```
    implementation 'com.github.CMzhizhe:NetWorkSdkJava:v1.0.0'

    implementation 'io.reactivex.rxjava3:rxjava:3.1.3'
    implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.8.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.8.1'
    implementation 'com.google.code.gson:gson:2.9.0'
    implementation 'com.squareup.moshi:moshi-kotlin:1.14.0'
```
如果在使用moshi遇到错误，在gradle.properties 文件配置如下信息
```
#moshi
android.jetifier.blacklist = com.squareup.moshi
```
#### 构建网络工具
```
 init {
        val builder = AbsMAFApiManager.Builder()
            .setCostTime(2 * 1000L) //设置方法请求超时接口回调
            .setReadTimeout(10)//秒单位
            .setConnectTimeoutSecond(10)//秒单位
            .setRequestUrl(Constant.BASE_REQUEST_V2) //设置base Url
            .setRetryOnConnectionFailure(false) //是否重新连接
            .setIsDevmodel(true) //是否开发者模式，开发者模式，会打印网络请求日志
            .setOnFactoryListener(FactoryImpl())//设置拦截器，AdapterFactorys
            .setOnObservableSourceStringListener(mResponseTransform)//将服务器提供的数据，进行转换
        mAbsMAFApiManagerImpl = AbsMAFApiManagerImpl(builder)
mAbsMAFMobileRequestImpl.setOnMAFApiManagerListener(mAbsMAFApiManagerImpl)//拿到get、post、put、
        setOnRequestResultCallBackConfigListener(this)//设置异常解析错误回调
    }
```

#### 请求介绍
* get请求
```
 异步
 AbsMAFMobileRequest.get()  //返回的结果是自己处理，也就是自己处理是数组，还是object
 异步---返回object，不是object，将会走fail，同理list
 AbsMAFMobileRequest.getResObj()
 异步---返回数组
 AbsMAFMobileRequest.getResList()
//============
 同步
 AbsMAFMobileRequest.getSync()  //返回的结果是自己处理，也就是自己处理是数组，还是object
 同步---返回object
 AbsMAFMobileRequest.getSyncResObj()
 同步---返回数组
 AbsMAFMobileRequest.getSyncResList()
```
* post请求
```
     表单请求
 异步
AbsMAFMobileRequest.postForm()
 异步---返回object，不是object，将会走fail，同理list
AbsMAFMobileRequest.postFormResObj()
 异步---返回数组
AbsMAFMobileRequest.postFormResList()
//============
 同步
AbsMAFMobileRequest.postSyncForm()
 同步---返回object
AbsMAFMobileRequest.postSyncFormResObj()
 同步---返回数组
AbsMAFMobileRequest.postSyncFormResList()

    json请求
 异步
AbsMAFMobileRequest.postBody()
 异步---返回object，不是object，将会走fail，同理list
AbsMAFMobileRequest.postBodyResObj()
 异步---返回数组
AbsMAFMobileRequest.postBodyResList()
//============
 同步
AbsMAFMobileRequest.postSyncBody()
 异步---返回object，不是object，将会走fail，同理list
AbsMAFMobileRequest.postSyncBodyResObj()
 异步---返回数组
AbsMAFMobileRequest.postSyncBodyResList()
```
* put请求
```
 异步
 AbsMAFMobileRequest.putBody()  //返回的结果是自己处理，也就是自己处理是数组，还是object
 异步---返回object，不是object，将会走fail，同理list
 AbsMAFMobileRequest.putBodyResObj()
 异步---返回数组
 AbsMAFMobileRequest.putBodyResList()
//============
 同步
 AbsMAFMobileRequest.putSyncBody()  //返回的结果是自己处理，也就是自己处理是数组，还是object
 同步---返回object
 AbsMAFMobileRequest.putSyncBodyResObj()
 同步---返回数组
 AbsMAFMobileRequest.putSyncBodyResList()
```

#### 获取自定义api
```
  val customApi = DemoMAFMobileRequestV2.getInstance().getApi(CustomApi::class.java)
```
#### 文件下载
```
AbsMAFMobileRequest.downFileProgress(downUrl,cacheDir.absolutePath,"${System.currentTimeMillis()}.jpg",fileDownLoadObserver)
```
#### 接口介绍
* OnObservableSourceStringListener
```
/**
 * @date 创建时间: 2022/3/13
 * @auther gaoxiaoxiong
 * @description TODO  处理服务器给的结果，转换成基类，需要解析code，status，targetJsonElement，也就是开发者需要自己根据服务器的具体情况自行修改
 **/
class ResponseTransform : OnObservableSourceStringListener {
    /**
     * @date 创建时间: 2022/3/14
     * @auther gaoxiaoxiong
     * @description 服务其返回的数据解析
     **/
    private class ResponseJsonFunction :
        Function<ResponseBody, Observable<String>> {
        @Throws(Exception::class)
        override fun apply(tResponse: ResponseBody): Observable<String> {
        TODO 服务其返回的数据解析，可以优先判断是不是自己想要的数据，比如判断是否200之类的
    }
    /**
     * @date 创建时间: 2022/3/13
     * @auther gaoxiaoxiong
     * @description 转换status code等需要的信息
     * @param jsonString 拿到 ResponseJsonFunction 提供的数据
     * @param method 请求的接口名称
     **/
    override fun doParseJson2StatusCodeMore(
        method: String,
        jsonString: String?
    ): BaseModel { //自己定义的BaseModel，需要实现 OnIParserListener接口
        TODO 在这里可以解析服务器提供的code，status，message，targetJsonElement
        最重要的是拿到 targetJsonElement，这个targetJsonElement 就是你希望的类型，一般都是data里面的数据
        return baseBean
    }
}
```
* OnIParserListener
```
/**
 * @author gaoxiaoxiong
 * @date 创建时间: 2023/6/29/029
 * @description
 * @param method 接口名称
 * @param resourceJsonString 服务器提供的整个原始数据
 * @param targetJsonElement 目标JsonElement，就是解析出的data里面的数据
 **/
class BaseModel(
    var method: String? = null,
    var resourceJsonString: String? = null,
    var targetJsonElement: JsonElement? = null
) : OnIParserListener {
    var message: String? = null
    var nu: String? = null
    var ischeck: String? = null
    var com: String? = null
    var status: String? = null
    var condition: String? = null
    var state: String? = null
    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  解析出的data里面的数据
     **/
    override fun onTargetJsonElement(): JsonElement? {
        return targetJsonElement
    }
    override fun onResourceJsonString(): String? {
        return resourceJsonString
    }
    override fun isSuccess(): Boolean {
       return status == "200"
    }
}
```
#### 将targetJsonElement转换成具体的对象
```
open class ParseResultDataImpl<T> : AbsOnRequestResultImpl() {
    private val TAG = "AbsDataResultCallback"
    private val RESPONSE_STATUS_STATUS_NEGATIVE_201="-201";//json解析失败

    override fun onRequestFail(
        throwable: Throwable?,
        status: String?,
        failMsg: String?,
        respon: String?,
        onIParserListener: OnIParserListener?
    ) {
        super.onRequestFail(throwable, status, failMsg, respon, onIParserListener)
        if (throwable!=null){
            if (throwable is SocketTimeoutException || throwable is ConnectException || throwable is UnknownHostException) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "出现网络异常")
                }
            }
        }
        onRequestDataFail(status?:"", failMsg?:"", onIParserListener as BaseModel?)
        onRequestBaseBeanFail(onIParserListener as BaseModel? )
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  成功
     * @param targetElement data里面的json
     **/
    override fun onRequestSuccess(
        method: String?,
        targetElement: JsonElement?,
        onIParserListener: OnIParserListener?,
    ) {
        super.onRequestSuccess(method, targetElement,onIParserListener)
        if (onIParserListener== null){
            return
        }
        if (targetElement!=null){
            var result:Any?=null
            try {
                val parameterizedType = this::class.java.genericSuperclass as ParameterizedType
                val subType =  parameterizedType.actualTypeArguments.first() //获取泛型T
                val adapter: JsonAdapter<Any> = MoshiUtil.moshi.adapter(subType)
                result = adapter.fromJson(targetElement.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                //处理解析异常
                onRequestFail(null,RESPONSE_STATUS_STATUS_NEGATIVE_201,"解析异常", null,onIParserListener)
                return
            }
            onRequestDataSuccess(if (result == null) null else result as T)
            onRequestBaseBeanSuccess(if (result == null) null else result as T,
                onIParserListener as BaseModel
            )
        }else{
            onRequestDataSuccess(null)
            onRequestBaseBeanSuccess(null, onIParserListener as BaseModel)
        }
    }
}

