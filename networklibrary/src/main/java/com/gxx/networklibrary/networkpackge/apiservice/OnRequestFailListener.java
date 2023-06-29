package com.gxx.networklibrary.networkpackge.apiservice;

import com.gxx.networklibrary.networkpackge.inter.OnIParserListener;

public interface OnRequestFailListener {
    void onRequestFail(Throwable throwable, String status, String failMsg, String errorJsonString, OnIParserListener onIParserListener);
}
