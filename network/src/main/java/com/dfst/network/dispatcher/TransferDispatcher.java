package com.dfst.network.dispatcher;

import com.dfst.network.builder.DownloadBuilder;
import com.dfst.network.builder.GetBuilder;
import com.dfst.network.builder.UploadBuilder;

/**
 * @author yanfei@supcon.com
 * 文件传输请求 分发器
 * @date 2016/3/31.
 */
public class TransferDispatcher implements Dispatcher {
    /**
     * 获得download请求构造器，通过get构造器构造download请求
     * @return
     */
    public DownloadBuilder download() {
        return new DownloadBuilder();
    }

    public GetBuilder fileLength() {
        return new GetBuilder();
    }

    /**
     * 获得上传请求构造器
     * @return
     */
    public UploadBuilder upload() {
        return new UploadBuilder();
    }
}
