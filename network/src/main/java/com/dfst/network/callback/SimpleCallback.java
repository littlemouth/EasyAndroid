package com.dfst.network.callback;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yanfei@supcon.com
 * Callback 空实现
 * @date 2016/3/24.
 */
public class SimpleCallback<T> implements Callback<T> {

    @Override
    public void onProgress(float progress) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onSuccess(T result) {

    }

    @Override
    public T parseResponse(Response response) throws Exception {
        return null;
    }
}
