package com.gxx.networkproject

import android.app.Application
import com.gxx.networkproject.demorequest.DemoMAFMobileRequest
import com.gxx.networkproject.demorequesttwo.DemoMAFMobileRequestV2

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化第一个域名
        DemoMAFMobileRequest.getInstance();
        //初始化第二个域名
        DemoMAFMobileRequestV2.getInstance()
    }
}