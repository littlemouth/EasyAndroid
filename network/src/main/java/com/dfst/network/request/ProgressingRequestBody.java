package com.dfst.network.request;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by yanfei on 2016/3/28.
 */
public class ProgressingRequestBody extends RequestBody {

    protected RequestBody delegate;
    protected Listener listener;

    protected CountingSink countingSink;
    private long per;
    private long total;
    private long limit;

    public ProgressingRequestBody(RequestBody delegate, Listener listener)
    {
        this.delegate = delegate;
        this.listener = listener;
        this.total = contentLength();
        this.per = total / 100 == 0 ? 1 : total / 100;
    }

    @Override
    public MediaType contentType()
    {
        return delegate.contentType();
    }

    @Override
    public long contentLength()
    {
        try
        {
            return delegate.contentLength();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException
    {

        countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink
    {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate)
        {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException
        {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            limit += byteCount;
            if (limit > per || bytesWritten == total) {
                listener.onRequestProgress(bytesWritten, total);
                limit = 0;
                if (bytesWritten == total) {
//                    Log.i("azpt", "=============length : " + total);
                }
            }

        }

    }

    public static interface Listener
    {
        public void onRequestProgress(long bytesWritten, long contentLength);
    }

}
