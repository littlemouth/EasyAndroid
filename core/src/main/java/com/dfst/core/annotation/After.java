package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * 该注解可用于初始化View，也可用于OnCreate() 方法后
 * 的操作。使用该注解方法在setContentView()后被调用
 * 同一Activity中多个方法使用该注解，方法调用顺序不
 * 确定，所以建议一个Activity中至多一个方法使用该注解。
 *
 * 该注解使用在method之上
 * @date 2016/3/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
}
