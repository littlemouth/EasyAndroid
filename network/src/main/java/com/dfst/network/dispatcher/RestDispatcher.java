package com.dfst.network.dispatcher;

import com.dfst.network.builder.RestBuilder;

/**
 * @author yanfei@supcon.com
 * @date 2016/3/31.
 */
public class RestDispatcher implements Dispatcher {
    /**
     * 获得 Rest 请求 构造器，指定为 get 请求
     * @return
     */
    public RestBuilder get() {
        return new RestBuilder(RestBuilder.METHOD.GET);
    }

    /**
     * 获得 Rest 请求 构造器，指定为 post 请求
     * @return
     */
    public RestBuilder post() {
        return new RestBuilder(RestBuilder.METHOD.POST);
    }

    /**
     * 获得 Rest 请求 构造器，指定为 put 请求
     * @return
     */
    public RestBuilder put() {
        return new RestBuilder(RestBuilder.METHOD.PUT);
    }

    /**
     * 获得 Rest 请求 构造器，指定为 delete 请求
     * @return
     */
    public RestBuilder delete() {
        return new RestBuilder(RestBuilder.METHOD.DELETE);
    }

    /**
     * 获得 Rest 请求 构造器，指定为 patch 请求
     * @return
     */
    public RestBuilder patch() {
        return new RestBuilder(RestBuilder.METHOD.PATCH);
    }
}
