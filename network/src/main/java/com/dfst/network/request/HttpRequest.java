package com.dfst.network.request;

import com.dfst.network.NetworkUtils;
import com.dfst.network.callback.Callback;
import com.dfst.network.callback.DownloadCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yanfei@supcon.com
 * 请求(request)基类
 * @date 2016/3/24.
 */
public abstract class HttpRequest {
    /** 请求 url */
    protected String url;
    /** 请求标签 */
    protected Object tag;
    /** 请求参数 */
    protected Map<String, String> params;
    /** 请求头 */
    protected Map<String, String> headers;

    /** okhttp Request.Builder 实例，用来创建okhttp request实例 */
    protected Request.Builder builder = new Request.Builder();

    /**
     * 构造函数
     * @param url
     * @param tag
     * @param headers
     * @param params
     */
    public HttpRequest(String url, Object tag, Map<String, String> headers,
                       Map<String, String> params) {
        this.url = url;
        this.tag = tag;
        this.headers = headers;
        this.params = params;

        builder.url(url).tag(tag);
        appendHeaders();
    }


    /**
     * 处理requestbody
     * @param requestBody
     * @param callback
     * @return
     */
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback)
    {
        if (callback instanceof DownloadCallback) {
            DownloadCallback downloadCallback = (DownloadCallback) callback;
            if (downloadCallback.isBreakPoint()) {
                File file = new File(downloadCallback.getDestFileDir(), downloadCallback.getDestFileName());
                if (file.exists()) {
                    long start = file.length();
                    long remoteContentLength = NetworkUtils.http().get().url(url).build().executeForContentLengthInUIThread();
                    if (start >= remoteContentLength) {
                        int index = 1;
                        File newFile = null;
                        String newFileName = null;
                        do {
                            String fullFileName = downloadCallback.getDestFileName();
                            String fileType = fullFileName.substring(fullFileName.lastIndexOf(".") + 1, fullFileName.length());
                            String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
                            newFileName = fileName + "("+ index++ +")." + fileType;
                            newFile = new File(downloadCallback.getDestFileDir(), newFileName);
                        } while(newFile.exists());
                        try {
                            newFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        downloadCallback.setDestFileName(newFileName);
                    } else {
                        builder.addHeader("Range", "bytes=" + start + "-");
                    }

                }
            }
        }
        return requestBody;
    }

    /**
     * 生成 okhttp requset
     * @param callback
     * @return
     */
    public Request generateRequest(Callback callback)
    {
        // 构建 RequestBody
        RequestBody requestBody = buildRequestBody();
        // 处理 RequestBody
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        // 构建 okhttp request
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    /**
     * 构建 RequestCall 实例
     * @return
     */
    public RequestCall build() {
        return new RequestCall(this);
    }

    /**
     * 构建 okhttp request
     * @param requestBody
     * @return okhttp Request 实例
     */
    protected abstract Request buildRequest(RequestBody requestBody);
    protected abstract RequestBody buildRequestBody();

    /**
     * 添加 headers 操作
     */
    protected void appendHeaders()
    {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet())
        {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }
}
