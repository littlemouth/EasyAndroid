package com.dfst.network.builder;

import com.dfst.network.request.GetRequest;
import com.dfst.network.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author yanfei@supcon.com
 * get 请求构造器
 * @date 2016/3/23.
 */
public class GetBuilder extends HttpRequestBuilder implements Paramsable {
    @Override
    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public GetBuilder tag(Object tag) {
        this.tag = tag;
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


    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        return new GetRequest(url, tag, headers, params).build();
    }

    @Override
    public GetBuilder addParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParam(String key, String value) {
        if (params == null) {
            params = new LinkedHashMap<String, String>();
        }
        this.params.put(key, value);
        return this;
    }
}
