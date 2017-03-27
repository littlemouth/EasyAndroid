package com.dfst.network.builder;


/**
 * @author yanfei@supcon.com
 * get 请求构造器
 * @date 2016/3/23.
 */
public class DownloadBuilder extends GetBuilder {
    @Override
    public DownloadBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public DownloadBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }
}
