package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * Bean 注入注解。将name索引指定的@AZEntity注册过的Bean的实例
 * 自动注入。
 *
 * 该注解使用在method之上
 * @date 2016/3/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    /**
     * name索引指向 @AZEntity 注解注册过的Bean
     * @return
     */
    String name();
}
