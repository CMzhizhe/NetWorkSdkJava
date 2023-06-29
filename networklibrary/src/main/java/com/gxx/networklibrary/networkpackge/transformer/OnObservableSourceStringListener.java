package com.gxx.networklibrary.networkpackge.transformer;

import com.gxx.networklibrary.networkpackge.inter.OnIParserListener;

import io.reactivex.rxjava3.core.ObservableTransformer;
import okhttp3.ResponseBody;

public interface OnObservableSourceStringListener {
    ObservableTransformer<ResponseBody, String> onObservableSource();

    /**
     * @date 创建时间: 2022/3/13
     * @auther gaoxiaoxiong
     * @description 解析json转换status code
     **/
    OnIParserListener doParseJson2StatusCodeMore(String method, String jsonString);
}
