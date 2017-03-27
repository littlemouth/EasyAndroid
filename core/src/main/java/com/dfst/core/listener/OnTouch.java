package com.dfst.core.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author yanfei@supcon.com
 * view 触摸事件类
 * 注解 @AZListener 中参数 type 值之一。
 * @date 2016/3/15.
 */
public class OnTouch extends OnListener implements View.OnTouchListener {
    @Override
    public void listener(View view) {
        view.setOnTouchListener(this);
    }

    /**
     * 调用父类invoke(Object... args) 方法，缺省不消耗触摸事件
     * @param v 目标View
     * @param event 触摸事件
     * @return 是否消耗触摸事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Object obj = invoke(v, event);
        if (obj instanceof Boolean) {
            return (boolean) obj;
        }
        return false;
    }
}
