package com.dfst.network.callback;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yanfei@supcon.com
 * 请求回调函数接口
 * @date 2016/3/24.
 */
public interface Callback<T> {

    /**
     * 请求执行进度，主要用于上传下载文件进度跟踪
     * UI Thread
     * @param progress 当前进度
     */
    public void onProgress(float progress);

    /**
     * 取消请求后执行的操作
     * UI Thread
     */
    public void onCancel();

    /**
     * 请求结束后的操作，不管成功还是失败，都会执行
     * UI Thread
     */
    public void onFinish();

    /**
     * 请求失败后的操作
     * UI Thread
     * @param e 失败原因
     */
    public void onError(Exception e);

    /**
     * 请求成功后的操作
     * UI Thread
     * @param result parseResponse(response) 方法中处理后的结果
     */
    public void onSuccess(T result);

    /**
     * Response 处理，在该方法中将response中的数据处理成想要的结果并返回
     * 非 UI Thread
     * @param response
     * @return T
     * @throws Exception
     */
    public T parseResponse(Response response) throws Exception;

    /**
     * 缺省callback，不做任何操作
     */
    public static Callback DEFAULT_CALLBACK = new SimpleCallback();
}
