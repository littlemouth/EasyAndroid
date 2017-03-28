package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * View 注入
 * 该注解可以自动注入布局中的View及布局外的view（如dialog）
 * 布局外view要求必须包含带有context唯一参数的构造器
 * 该注解使用在field之上
 * @date 2016/3/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AView {
    /**
     * View 注入
     * @return 要注入view的id，缺省value值默认到布局文件中查找id为 R.id.fieldname的view
     */
    int value() default 0;
}
