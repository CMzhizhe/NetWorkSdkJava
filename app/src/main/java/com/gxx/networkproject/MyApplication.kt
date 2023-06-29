package com.gxx.networkproject

import android.app.Application
import com.gxx.networkproject.demorequest.DemoMAFMobileRequest
import com.gxx.networkproject.demorequesttwo.DemoMAFMobileRequestV2

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DemoMAFMobileRequest.getInstance();

        DemoMAFMobileRequestV2.getInstance()
    }
}