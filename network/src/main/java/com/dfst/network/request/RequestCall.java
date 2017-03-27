package com.dfst.network.request;

import com.dfst.network.NetworkUtils;
import com.dfst.network.callback.Callback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yanfei@supcon.com
 * request 封装类
 * @date on 2016/3/23.
 */
public class RequestCall {
    /** okhttp Call */
    private Call call;
    /** okhttp Request */
    private Request request;
    private Response response;
    private String result;
    private long contentLength;
    private HttpRequest httpRequest;
    private OkHttpClient client;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    private boolean hasReturn;

    public RequestCall(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.hasReturn = false;
    }

    public Call getCall() {
        return call;
    }

    /**
     * 构建 okhttp Call 实例
     * @param callback
     * @return Call
     */
    public Call buildCall(Callback callback) {
        request = httpRequest.generateRequest(callback);
        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0)
        {
            readTimeOut = readTimeOut > 0 ? readTimeOut : NetworkUtils.getInstance().getReadTimeout();
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : NetworkUtils.getInstance().getWriteTimeout();
            connTimeOut = connTimeOut > 0 ? connTimeOut : NetworkUtils.getInstance().getConnectTimeout();

            client = NetworkUtils.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            call = client.newCall(request);
        } else
        {
            call = NetworkUtils.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    public RequestCall readTimeOut(long readTimeOut)
    {
        this.readTimeOut = readTimeOut;
        return this;
    }
    public RequestCall writeTimeOut(long writeTimeOut)
    {
        this.writeTimeOut = writeTimeOut;
        return this;
    }
    public RequestCall connTimeOut(long connTimeOut)
    {
        this.connTimeOut = connTimeOut;
        return this;
    }

    /**
     * UI thread 同步请求
     * @return Response
     */
    public Response executeInUIThread() {
        buildCall(null);
        new Thread() {
            public  void run() {
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    hasReturn = true;
                }
            }
        }.start();

        while (!hasReturn) {}

        return response;
    }

    /**
     * 同步请求
     * @return response
     */
    public Response excute() {
        buildCall(null);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 同步请求
     * @return String 类型 返回结果
     */
    public String executeForString() {
        buildCall(null);
        try {
            result = call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String executeForStringInUIThread() {
        buildCall(null);
        new Thread() {
            public void run() {
                try {
                    result = call.execute().body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    hasReturn = true;
                }
            }
        }.start();
        while (!hasReturn) {}
        return result;
    }

    /**
     * 获取远程文件大小
     * @return
     * @throws IOException
     */
    public long executeForContentLength() {
        buildCall(null);
        try {
            contentLength = call.execute().body().contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  contentLength;
    }

    public long executeForContentLengthInUIThread(){
        buildCall(null);
        new Thread() {
            public void run() {
                try {
                    contentLength = call.execute().body().contentLength();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    hasReturn = true;
                }
            }
        }.start();
        while (!hasReturn) {}
        return contentLength;
    }

    /**
     * 异步请求
     * @param callback
     */
    public void execute(Callback callback) {
        buildCall(callback);
        NetworkUtils.getInstance().execute(this, callback);
    }

    /**
     * 异步请求 Ui thread
     * @param callback
     */
    public void executeInUIThread(final Callback callback) {
        buildCall(callback);
        new Thread() {
            public void run() {
                NetworkUtils.getInstance().execute(RequestCall.this, callback);
            }
        }.start();

    }

    /**
     * 取消该请求
     */
    public void cancel()
    {
        if (call != null)
        {
            call.cancel();
        }
    }
}
