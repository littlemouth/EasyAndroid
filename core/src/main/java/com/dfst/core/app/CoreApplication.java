package com.dfst.core.app;

import android.app.Application;

import com.dfst.core.base.Ioc;

/**
 * Created by yanfei on 2016/3/15.
 */
public class CoreApplication extends Application {
    @Override
    public void onCreate() {
        // ioc 入口
        Ioc.getIoc().init(this);
        super.onCreate();
    }
}
