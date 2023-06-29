package com.gxx.networklibrary.networkpackge.customobserver.filedownobserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 创建时间: 2019/7/21
 * gxx
 * 注释描述:文件下载的observer
 */
public abstract class FileDownLoadObserver<T> implements Observer<T> {
    private Disposable disposable;

    @Override
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownLoadFail(e);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    //可以重写，具体可由子类实现
    @Override
    public void onComplete() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    //下载成功的回调
    public abstract void onDownLoadSuccess(T t);

    //下载失败回调
    public abstract void onDownLoadFail(Throwable throwable);

    //下载进度监听  progress百分比  progressLong当前下载量   total总量
    public abstract void onProgress(int progress, long progressLong, long total);

    /**
     * 将文件写入沙盒目录下面
     * @param requestUrl   请求地址
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFileToLocal(String requestUrl, String destFileDir, String destFileName) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(15 * 1000);
        InputStream is = conn.getInputStream();
        File file = new File(destFileDir, destFileName);

        if (file.exists()){
            file.delete();
        }

        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        if (!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        long contentLeng  = conn.getContentLength();
        try {
            byte[] buffer = new byte[1024];
            int len;
            long total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                onProgress((int) (total * 100 / contentLeng), total, contentLeng);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fos.close();
            bis.close();
            is.close();
        }
       return file;
    }
}
