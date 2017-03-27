package com.dfst.network.request;

import com.dfst.network.builder.RestBuilder;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yanfei@supcon.com
 * Rest 请求封装类
 * @date 2016/3/30.
 */
public class RestRequest extends HttpRequest {
    /** 请求方法名 */
    private String method;
    public RestRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params, String method) {
        super(url, tag, headers, params);
        this.method = method;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        if (method.equals(RestBuilder.METHOD.GET)) {
            builder.get();
        } else if (method.equals(RestBuilder.METHOD.POST)) {
            builder.post(requestBody);
        } else if (method.equals(RestBuilder.METHOD.PUT)) {
            builder.put(requestBody);
        } else if (method.equals(RestBuilder.METHOD.PATCH)) {
            builder.patch(requestBody);
        } else if (method.equals(RestBuilder.METHOD.DELETE)) {
            if (requestBody == null) {
                builder.delete();
            } else {
                builder.delete(requestBody);
            }

        }
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return new FormBody.Builder().build();
    }
}
