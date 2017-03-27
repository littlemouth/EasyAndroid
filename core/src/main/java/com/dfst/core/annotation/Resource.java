package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * android 资源 注入, 目前支持string、int、string数组、
 * int数组资源注入。
 *
 * 该注解用于field之上
 * @date 2016/3/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
    /**
     * 获取资源id，id缺省默认寻找R.id.fieldname 资源
     * @return 资源id
     */
    int value() default 0;
}
