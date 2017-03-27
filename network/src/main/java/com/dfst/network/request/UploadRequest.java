package com.dfst.network.request;

import com.dfst.network.NetworkUtils;
import com.dfst.network.callback.Callback;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author yanfei@supcon.com
 * 上传请求 封装类
 * @date 2016/3/29.
 */
public class UploadRequest extends HttpRequest {
    /** 缺省 contentType */
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    /** 上传的文件 */
    private File file;
    private MediaType mediaType;

    public UploadRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file, MediaType mediaType)
    {
        super(url, tag, params, headers);
        this.file = file;
        this.mediaType = mediaType;

        if (this.file == null)
        {

        }
        if (this.mediaType == null)
        {
            this.mediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return RequestBody.create(MediaType.parse(getMimeType(file.getName())), file);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.post(requestBody).build();
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

    /**
     * 判断 contentType
     * @param path
     * @return
     */
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
