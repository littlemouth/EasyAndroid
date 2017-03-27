package com.dfst.network;

import android.os.Handler;
import android.os.Looper;

import com.dfst.network.callback.Callback;
import com.dfst.network.dispatcher.HttpDispatcher;
import com.dfst.network.dispatcher.RestDispatcher;
import com.dfst.network.dispatcher.TransferDispatcher;
import com.dfst.network.request.RequestCall;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author yanfei@supcon.com
 * network 工具类
 * @date 2016/3/23.
 */
public class NetworkUtils {
    /** 网络请求 callback 容器， 用来保存正在进行和队列中请求的callback函数 */
    private static final Map<Object, Callback> callbackMap = new Hashtable<Object, Callback>();
    /** 网络 连接 超时时间 */
    private long connectTimeout = 10000;
    /** 读取 超时时间 */
    private long readTimeout = 10000;
    /** 写入 连接 超时时间 */
    private long writeTimeout = 10000;
    /** AZNetworkUtils 单例 */
    private static NetworkUtils mInstance;
    private static TransferDispatcher transfereDispatcher;
    private static RestDispatcher restDispatcher;
    private static HttpDispatcher httpDispatcher;
    /** OkHttpClient 实例 */
    private OkHttpClient mOkhttpClient;
    private Handler handler;

    public NetworkUtils(OkHttpClient okhttpClient) {
        if (mInstance == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            mOkhttpClient = builder.build();
        } else {
            mOkhttpClient = okhttpClient;
        }
        handler = new Handler(Looper.getMainLooper());
    }

    public OkHttpClient getOkHttpClient() {
        return this.mOkhttpClient;
    }

    public Handler getHandler() {
        return handler;
    }

    public static NetworkUtils getInstance(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (NetworkUtils.class) {
                if (mInstance == null) {
                    mInstance = new NetworkUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static NetworkUtils getInstance() {
        if (mInstance == null) {
            synchronized (NetworkUtils.class) {
                if (mInstance == null) {
                    mInstance = new NetworkUtils(null);
                }
            }
        }
        return mInstance;
    }

    /**
     * 常规http get post请求入口
     * @return httpDispatcher http请求分发器
     */
    public static HttpDispatcher http() {
        if (httpDispatcher == null) {
            synchronized (NetworkUtils.class) {
                if (httpDispatcher == null) {
                    httpDispatcher = new HttpDispatcher();
                }
            }
        }
        return httpDispatcher;

    }

    /**
     * 网络文件传输 入口
     * @return transfereDispatcher 文件传输请求分发器
     */
    public static TransferDispatcher transfer() {
        if (transfereDispatcher == null) {
            synchronized (NetworkUtils.class) {
                if (transfereDispatcher == null) {
                    transfereDispatcher = new TransferDispatcher();
                }
            }
        }
        return transfereDispatcher;
    }

    /**
     * Rest 风格请求 入口
     * @return
     */
    public static RestDispatcher rest() {
        if (restDispatcher == null) {
            synchronized (NetworkUtils.class) {
                if (restDispatcher == null) {
                    restDispatcher = new RestDispatcher();
                }
            }
        }
        return restDispatcher;
    }

    /**
     * 执行配置好的网络请求
     * @param requestCall request call
     * @param callback 回调函数
     */
    public void execute(RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.DEFAULT_CALLBACK;
        }
        final Callback finalCallback = callback;
        Call call = requestCall.getCall();
        if (call == null) return;
        if (call.request().tag() != null) {
            callbackMap.put(call.request().tag(), callback);
        }
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finalCallback.onFinish();
                        finalCallback.onError(e);
                    }
                });
                callbackMap.remove(call.request().tag());

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 400 && response.code() <= 599) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            finalCallback.onFinish();
                            finalCallback.onError(new RuntimeException(response.body().toString()));
                        }
                    });
                    return;
                }

                try {
                    final Object o = finalCallback.parseResponse(response);
                    handler.post(new Runnable() {
                        public void run() {
                            finalCallback.onFinish();
                            finalCallback.onSuccess(o);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        public void run() {
                            finalCallback.onError(e);
                        }
                    });
                    e.printStackTrace();
                } finally {
                    callbackMap.remove(call.request().tag());
                }

            }
        });

    }

    /**
     * 根据 标签 tag 取消请求
     * @param tag
     */
    public void cancelTag(Object tag)
    {
        for (final Call call : mOkhttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
                handler.post(new Runnable() {
                    public void run() {
                        Callback callback = callbackMap.get(call.request().tag());
                        if (callback != null) {
                            callbackMap.get(call.request().tag()).onCancel();
                            callbackMap.remove(call.request().tag());
                        }
                    }
                });
            }
        }
        for (final Call call : mOkhttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
                handler.post(new Runnable() {
                    public void run() {
                        Callback callback = callbackMap.get(call.request().tag());
                        if (callback != null) {
                            callbackMap.get(call.request().tag()).onCancel();
                            callbackMap.remove(call.request().tag());
                        }

                    }
                });
            }
        }
    }

    public void setConnectTimeout(int milliSeconds)
    {
        mOkhttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(milliSeconds, TimeUnit.MILLISECONDS)
                .build();
        this.connectTimeout = milliSeconds;

    }

    public void setReadTimeout(int milliSeconds)
    {
        mOkhttpClient = getOkHttpClient().newBuilder()
                .readTimeout(milliSeconds, TimeUnit.MILLISECONDS)
                .build();
        this.readTimeout = milliSeconds;
    }

    public void setWriteTimeout(int milliSeconds)
    {
        mOkhttpClient = getOkHttpClient().newBuilder()
                .writeTimeout(milliSeconds, TimeUnit.MILLISECONDS)
                .build();
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }
}
