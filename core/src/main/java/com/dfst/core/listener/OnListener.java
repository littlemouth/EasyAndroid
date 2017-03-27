package com.dfst.core.listener;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yanfei@supcon.com
 * 框架中事件基类
 * @date 2016/3/15
 */
public abstract class OnListener {
    /** 使用 @AZListener 注解的方法的持有对象 */
    private Object target;
    /** 使用 @AZListener 注解的方法 */
    private Method method;

    /**
     * 绑定事件入口方法
     * @param view 需要绑定事件的view
     * @param target 方法持有对象
     * @param method 使用 @AZListener 注解的方法
     */
    public final void listener(View view, Object target, Method method) {
        this.method = method;
        this.target = target;
        listener(view);
    }

    /**
     * 回调目标method
     * @param args 参数类型、数量、顺序必须与监听事件实现方法的一致
     * @return
     */
    public Object invoke(Object... args) {
        if (method != null) {
            try {
                return method.invoke(target, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 子类实现该方法来绑定特定事件
     * @param view 需要绑定事件的view
     */
    public abstract void listener(View view);
}
