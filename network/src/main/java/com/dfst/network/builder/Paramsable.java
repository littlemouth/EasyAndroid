package com.dfst.network.builder;

import java.util.Map;

/**
 * @author yanfei@supcon.com
 *  添加请求参数接口
 * @date 2016/3/24.
 */
public interface Paramsable {
    /**
     * 批量添加请求参数
     * @param params Map 格式的键值对
     * @return HttpRequestBuilder
     */
    public HttpRequestBuilder addParams(Map<String, String> params);

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return HttpRequestBuilder
     */
    public HttpRequestBuilder addParam(String key, String value);
}
