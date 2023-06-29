package com.gxx.networklibrary.networkpackge.customobserver;

import android.text.TextUtils;

import com.gxx.networklibrary.networkpackge.exceptonhandle.ExceptionHandle;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {
    private Disposable disposable;

    @Override
    public void onError(Throwable e) {
        String errorMessage = "";
        String errorJsonString = "";
        if (e instanceof ExceptionHandle.ServerException) {
            ExceptionHandle.ServerException error = ((ExceptionHandle.ServerException) e);
            if (!TextUtils.isEmpty(error.message)) {
                errorMessage = error.message;
            }

            if (!TextUtils.isEmpty(error.jsonString)){
                errorJsonString = error.jsonString;
            }

            onError(e,error.code, errorMessage, errorJsonString);
        } else if (e instanceof ExceptionHandle.ResponeThrowable) {
            ExceptionHandle.ResponeThrowable error = (ExceptionHandle.ResponeThrowable) e;
            if (!TextUtils.isEmpty(error.message)) {
                errorMessage = error.message;
            }
            onError(e,error.code, errorMessage, null);
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        onDisposable(this.disposable);
    }

    //onError跟onComplete是互斥的
    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public abstract void onDisposable(Disposable disposable);

    public abstract void onError(Throwable throwable,String status, String msg, String jsonString);
}