package com.dfst.network.builder;

import com.dfst.network.request.RequestCall;
import com.dfst.network.request.RestRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * Rest 请求 构造器
 * @date 2016/3/30.
 */
public class RestBuilder extends HttpRequestBuilder implements Paramsable  {
    private String method = METHOD.GET;

    /**
     * 构造函数
     * @param method 方法名，用来标示 请求 类型，支持 GET, POST, PUT, DELETE, PATCH
     */
    public RestBuilder(String method) {
        this.method = method;
    }

    @Override
    public RestBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        return new RestRequest(url, tag, headers, params, method).build();
    }

    @Override
    public RestBuilder addParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public RestBuilder addParam(String key, String value) {
        if (params == null) {
            params = new LinkedHashMap<String, String>();
        }
        this.params.put(key, value);
        return this;
    }

    protected String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static interface METHOD
    {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}
