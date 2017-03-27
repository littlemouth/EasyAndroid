package com.dfst.core.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dfst.core.base.InjectCore;

/**
 * @author yanfei@supcon.com
 * 自定义Instrumentation，用来监控Activity生命周期方法
 * @date 2016/3/15.
 */
public class InstrumentationBean extends Instrumentation {
    private boolean isVerified = false;

    public boolean isVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        // activity inject 入口
        // 当前activity是否是AppCompatActivity
        boolean isAppCompatActivity = activity instanceof AppCompatActivity;
        // step1 inject resource
        InjectCore.injectResources(activity);
        // step2 inject entity;
        InjectCore.injectEntity(activity);
        // step3 inject before;
        InjectCore.beforeOnCreate(activity);

        super.callActivityOnCreate(activity, icicle);
        // step4 inject layout;
        InjectCore.injectContentView(activity);
        // step5 inject view
        InjectCore.injectView(activity, null);
        // step6 inject after
        InjectCore.afterOnCreate(activity);
        // step7 inject listener
        InjectCore.injectListener(activity, null);
    }
}
