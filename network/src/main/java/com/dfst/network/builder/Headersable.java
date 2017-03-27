package com.dfst.network.builder;

import java.util.Map;

/**
 * @author yanfei@supcon.com
 * 添加请求头接口
 * @date 2016/3/28.
 */
public interface Headersable {
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public HttpRequestBuilder addHeader(String key, String value);

    /**
     * 批量添加请求头
     * @param headers map格式键值对
     * @return
     */
    public HttpRequestBuilder headers(Map<String, String> headers);
}
