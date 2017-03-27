package com.dfst.network.callback;

import okhttp3.Response;

/**
 * Created by yanfei on 2016/4/6.
 */
public class StringCallback extends SimpleCallback<String> {
    @Override
    public String parseResponse(Response response) throws Exception {
        return response.body().string();
    }

}
