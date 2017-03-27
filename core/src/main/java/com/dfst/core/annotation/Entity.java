package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * Bean 注解，使用该注解的Bean要确保有默认构造函数。
 * 框架内有Bean容器管理Bean实例生命周期，可通过设置
 * singleton值决定该Bean是否是单例模式。如果是单例
 * 模式，则第一次使用时创建该Bean的实例，并将其置入
 * 缓存供再此使用；如果不是单例模式，每次使用都会创
 * 建一个新的Bean实例
 * @date 2016/3/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    /**
     * Bean 的索引，通过该索引值获取该Bean的实例
     * @return 索引值
     */
    String value() default "";

    /**
     * 标示该 Bean 是否是单例模式。默认为单例模式
     * @return
     */
    boolean singleton() default true;
}
