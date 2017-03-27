package com.dfst.core.annotation;

import com.dfst.core.constant.ListenerType;
import com.dfst.core.listener.OnClick;
import com.dfst.core.listener.OnListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * Android 事件注入。目前支持常用事件View的单击事件（ListenerType.Click）、
 * 长点击事件（ListenerType.LongClick）、触摸事件（ListenerType.Touch），以及AdapterView
 * 的子项点击事件（ListenerType.OnItemClick）。支持一个方法绑定到多个View缺省
 * 为OnClick事件
 * 该注解使用在方法上。e.g. 绑定单击事件 @AZListener({R.id.button1})，
 * 绑定触摸事件 @AZListener(value={R.id.view1, R.id.}, ListenerType.Click)
 *
 * 该注解使用在method之上
 * @date 2016/3/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    /**
     * 设置绑定事件的View
     * @return 需绑定事件所有View的id数组
     */
    int[] value();

    /**
     * 指定绑定事件类型，缺省绑定单击事件（OnClick.class）
     * @return
     */
    ListenerType type() default ListenerType.Click;
}
