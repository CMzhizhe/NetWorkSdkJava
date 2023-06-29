package com.gxx.networkproject.mvvm.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.gxx.networklibrary.networkpackge.apiservice.OnDisposablesListener
import com.gxx.networklibrary.networkpackge.customobserver.BaseObserver
import com.gxx.networklibrary.networkpackge.customobserver.filedownobserver.FileDownLoadObserver
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener
import com.gxx.networklibrary.networkpackge.schedulers.SchedulerProvider
import com.gxx.networkproject.BuildConfig
import com.gxx.networkproject.api.CustomApi
import com.gxx.networkproject.constant.Constant
import com.gxx.networkproject.demorequest.DemoMAFMobileRequest
import com.gxx.networkproject.demorequest.basemodel.BaseBean
import com.gxx.networkproject.demorequest.parse.ParseResultDataImpl
import com.gxx.networkproject.demorequesttwo.DemoMAFMobileRequestV2
import com.gxx.networkproject.demorequesttwo.basemodel.BaseModel
import com.gxx.networkproject.demorequesttwo.parse.ResponseTransform
import com.gxx.networkproject.model.ArticleBean
import com.gxx.networkproject.model.KusiDiModel
import com.gxx.networkproject.mvvm.activity.MainActivity
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File
import java.util.*

class MainViewModel : ViewModel(), OnDisposablesListener {
    private val mDisposableList = LinkedList<Disposable>()
    private val TAG = "MainViewModel"
    override fun onCleared() {
        super.onCleared()
        for (disposable in mDisposableList) {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
        }
        mDisposableList.clear()
    }

    override fun onDisposable(disposable: Disposable?) {
        if (disposable != null) {
            mDisposableList.add(disposable)
        }
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  图片文件下载
     **/
    fun downPicFile(context: Context,downUrl:String){
        DemoMAFMobileRequest.getInstance().downPicFile(context,downUrl,object :FileDownLoadObserver<File>(){
            override fun onDownLoadSuccess(t: File?) {
                if (t!=null){
                    Log.d(TAG,"文件下载的地址=${t.absolutePath}")
                }
            }

            override fun onDownLoadFail(throwable: Throwable?) {

            }

            override fun onProgress(progress: Int, progressLong: Long, total: Long) {
                Log.d(TAG,"下载进度百分比=${progress}%")
            }
        })
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/23/023
     * @description  get 请求数据
     **/
    fun readPosts() {
        val requestUrl = Constant.getBaseRequest("posts")
        DemoMAFMobileRequest.getInstance()
            .get(requestUrl, null, object : ParseResultDataImpl<MutableList<ArticleBean>>() {
                override fun onRequestDataSuccess(data: MutableList<ArticleBean>?) {
                    super.onRequestDataSuccess(data)
                    if (BuildConfig.DEBUG) {
                        Log.d(MainActivity.TAG, "data第一个数据UserId：${data?.first()?.userId}");
                    }
                }

                override fun onRequestBaseBeanFail(baseBean: BaseBean) {
                    super.onRequestBaseBeanFail(baseBean)
                }
            }, this)
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  get的同步请求
     **/
    fun readkuaidiSync(type: String, posId: String) {
        val requestUrl = Constant.getBaseRequestV2("query")
        val map = mutableMapOf<String, String>()
        map["type"] = type;
        map["postid"] = posId;
        Log.d(TAG, "同步开始请求")
        DemoMAFMobileRequestV2.getInstance().getSync(requestUrl, map, object :
            com.gxx.networkproject.demorequesttwo.parse.ParseResultDataImpl<MutableList<KusiDiModel>>() {
            override fun onRequestBaseBeanSuccess(
                data: MutableList<KusiDiModel>?,
                baseBean: BaseModel
            ) {
                super.onRequestBaseBeanSuccess(data, baseBean)
                Log.d(TAG, "同步拿到了结果了")
            }

            override fun onRequestDataFail(code: String, msg: String, baseBean: BaseModel?) {
                super.onRequestDataFail(code, msg, baseBean)
            }
        }, this)
        Log.d(TAG, "同步结束请求")
    }

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  快递查询
     * @param type 快递类型 快递公司编码:申通="shentong" EMS="ems" 顺丰="shunfeng" 圆通="yuantong" 中通="zhongtong" 韵达="yunda" 天天="tiantian" 汇通="huitongkuaidi" 全峰="quanfengkuaidi" 德邦="debangwuliu" 宅急送="zhaijisong"
     * @param posId 快递单号
     **/
    fun readkuaidi(type: String, posId: String) {
        val requestUrl = Constant.getBaseRequestV2("query")
        val map = mutableMapOf<String, String>()
        map["type"] = type;
        map["postid"] = posId;
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

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  自定义customApi
     **/
    fun readCutomApiKuaiDi(type: String, posId: String) {
        val requestUrl = Constant.getBaseRequestV2("query")
        val map = mutableMapOf<String, String>()
        map["type"] = type;
        map["postid"] = posId;
        val customApi = DemoMAFMobileRequestV2.getInstance().getApi(CustomApi::class.java)
        customApi.getJsonRequest(requestUrl, map)
            .compose(ResponseTransform().onObservableSource())
            .map { t -> ResponseTransform().doParseJson2StatusCodeMore(requestUrl, t) }
            .compose(SchedulerProvider.getInstance().applySchedulers())
            .subscribe(object : BaseObserver<OnIParserListener>() {

                override fun onNext(t: OnIParserListener) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "是否正常：${t.isSuccess}");
                        Log.d(TAG, "targetJson：${t.onTargetJsonElement().asJsonArray.toString()}");
                    }
                }

                override fun onError(
                    throwable: Throwable?,
                    status: String?,
                    msg: String?,
                    jsonString: String?
                ) {

                }

                override fun onDisposable(disposable: Disposable?) {

                }
            })
    }
}