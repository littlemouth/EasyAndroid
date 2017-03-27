package com.dfst.network.builder;

import com.dfst.network.request.RequestCall;
import com.dfst.network.request.UploadRequest;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author yanfei@supcon.com
 * 文件上传请求构造器，只支持单文件上传
 * @date 2016/3/28.
 */
public class UploadBuilder extends HttpRequestBuilder {
    private File file;
    private MediaType mediaType;

    /**
     * 添加要上传的文件
     * @param file
     * @return UploadBuilder
     */
    public UploadBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    /**
     * 指定contentType 不设置自行判断
     * @param mediaType
     * @return UploadBuilder
     */
    public UploadBuilder mediaType(String mediaType)
    {
        this.mediaType = MediaType.parse(mediaType);
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new UploadRequest(url, tag, params, headers, file, mediaType).build();
    }

    @Override
    public UploadBuilder url(String url)
    {
        this.url = url;
        return this;
    }

    @Override
    public UploadBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public UploadBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public UploadBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
