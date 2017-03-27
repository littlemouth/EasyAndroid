package com.dfst.network.builder;

import com.dfst.network.request.PostRequest;
import com.dfst.network.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * post 请求 构造器
 * @date 2016/3/28.
 */
public class PostBuilder extends HttpRequestBuilder implements Paramsable {
    private List<PostFile> files = new ArrayList<>();

    @Override
    public PostBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostRequest(url, tag, headers, params, files).build();
    }

    @Override
    public PostBuilder addParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostBuilder addParam(String key, String value) {
        if (params == null) {
            params = new LinkedHashMap<String, String>();
        }
        params.put(key, value);
        return this;
    }

    /**
     * 添加 文件
     * @param name form 中 key值 <input name="">
     * @param filename 文件名
     * @param file 文件
     * @return PostBuilder
     */
    public PostBuilder addFile(String name, String filename, File file)
    {
        files.add(new PostFile(name, filename, file));
        return this;
    }

    /**
     * 批量添加文件
     * @param name form 中 key值 <input name="">
     * @param fileMap Map<文件名， 文件>
     * @return PostBuilder
     */
    public PostBuilder addFiles(String name, Map<String, File> fileMap) {
        for (String fileName : fileMap.keySet()) {
            files.add(new PostFile(name, fileName, fileMap.get(fileName)));
        }
        return this;
    }

    @Override
    public PostBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public PostBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }

    /**
     * 文件封装类
     */
    public static class PostFile
    {
        public String key;
        public String filename;
        public File file;

        public PostFile(String name, String filename, File file)
        {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString()
        {
            return "PossFile{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
}
