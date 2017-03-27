package com.dfst.network.request;

import com.dfst.network.NetworkUtils;
import com.dfst.network.builder.PostBuilder;
import com.dfst.network.callback.Callback;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yanfei on 2016/3/28.
 */
public class PostRequest extends HttpRequest {
    private List<PostBuilder.PostFile> files;

    public PostRequest(String url, Object tag, Map<String, String> headers,
                       Map<String, String> params, List<PostBuilder.PostFile> files) {
        super(url, tag, headers, params);
        this.files = files;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {
            return buildFormBody();
        } else {
            return builderMultipartBody();
        }
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) return requestBody;

//        try {
//            Log.i("azpt", "------- Total Length  " + requestBody.contentLength() + "  --------");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ProgressingRequestBody countingRequestBody = new ProgressingRequestBody(requestBody, new ProgressingRequestBody.Listener()
        {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength)
            {

                NetworkUtils.getInstance().getHandler().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        callback.onProgress(bytesWritten * 1.0f / contentLength);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    private FormBody buildFormBody() {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null)
        {
            for (String key : params.keySet())
            {
                builder.add(key, params.get(key));
            }
        }
        return builder.build();
    }

    private MultipartBody builderMultipartBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                //builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                       // RequestBody.create(null, params.get(key)));
                builder.addFormDataPart(key, params.get(key));
            }
        }

        for (PostBuilder.PostFile file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(getMimeType(file.filename)), file.file);
            builder.addFormDataPart(file.key, file.filename, fileBody);
        }
        return builder.build();
    }

    private String getMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
