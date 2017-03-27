package com.dfst.network.dispatcher;

import com.dfst.network.builder.GetBuilder;
import com.dfst.network.builder.PostBuilder;

/**
 * @author yanfei@supcon.com
 * http 请求分发器
 * @date 2016/3/31.
 */
public class HttpDispatcher implements Dispatcher {
    /**
     * 获得 get 请求 构造器
     * @return GetBuilder
     */
    public GetBuilder get() {
        return new GetBuilder();
    }

    /**
     * 获得 post 请求 构造器
     * @return
     */
    public PostBuilder post() {
        return new PostBuilder();
    }
}
