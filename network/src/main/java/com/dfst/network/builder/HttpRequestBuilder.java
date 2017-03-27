package com.dfst.network.builder;

import com.dfst.network.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * 请求 builder 基类
 * @date 2016/3/24.
 */
public abstract class HttpRequestBuilder implements Headersable {
    /** 请求url */
    protected String url;
    /** 请求标签 */
    protected Object tag;
    /** 请求头 */
    protected Map<String, String> headers;
    /** 请求参数 */
    protected Map<String, String> params;

    /**
     * 设置 请求 url
     * @param url
     * @return HttpRequestBuilder
     */
    public abstract HttpRequestBuilder url(String url);

    /**
     * 设置标签
     * @param tag
     * @return HttpRequestBuilder
     */
    public abstract HttpRequestBuilder tag(Object tag);

    /**
     * 构建 RequestCall 实例
     * @return RequestCall
     */
    public abstract RequestCall build();

    @Override
    public HttpRequestBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public HttpRequestBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
