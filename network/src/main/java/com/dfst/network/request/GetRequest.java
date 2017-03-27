package com.dfst.network.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yanfei@supcon.com
 * get request 封装类
 * @date 2016/3/23.
 */
public class GetRequest extends HttpRequest {

    public GetRequest(String url, Object tag, Map<String, String> headers,
                      Map<String, String> params) {
        super(url, tag, headers, params);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

}
