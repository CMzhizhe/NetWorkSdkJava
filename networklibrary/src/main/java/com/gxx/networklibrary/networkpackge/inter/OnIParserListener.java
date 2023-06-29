package com.gxx.networklibrary.networkpackge.inter;

import com.google.gson.JsonElement;

public interface OnIParserListener {

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  获取目标的targetJsonElement
     **/
    JsonElement onTargetJsonElement();

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  获取服务器提供的原数据
     **/
    String onResourceJsonString();

    /**
     * @author gaoxiaoxiong
     * @date 创建时间: 2023/6/29/029
     * @description  是否成功
     **/
    boolean isSuccess();
}
