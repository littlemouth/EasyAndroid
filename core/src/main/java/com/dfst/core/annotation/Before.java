package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author  yanfei@supcon.com
 * 该注解用于Activity的UI初始化之前的操作。使用该注解
 * 的方法在OnCreate方法前被调用。
 * 同一Activity中多个方法使用该注解，调用顺序呢不确定，
 * 因此，建议同一Activity中至多使用一次该注解
 *
 * 该注解使用在method之上
 * @date 2016/3/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
}
