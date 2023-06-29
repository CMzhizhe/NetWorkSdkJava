package com.gxx.networklibrary.networkpackge.apiservice;

import com.google.gson.JsonElement;
import com.gxx.networklibrary.networkpackge.inter.OnIParserListener;

public interface OnRequestSuccessListener {
    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/25/025
     * @description  成功回调
     * @param  method 请求的接口名称
     * @param targetElement 最终期望的json
     **/
    void onRequestSuccess(String method, JsonElement targetElement,OnIParserListener onIParserListener);
}
