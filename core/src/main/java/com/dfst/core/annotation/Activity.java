package com.dfst.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanfei@supcon.com
 * Activity布局文件注入，用于注入Activity的布局文件资源
 *
 * 该注解使用在Activity类之上
 * @date 2016/3/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Activity {
    /**
     * 布局文件注入
     * @return 布局文件id，e.g,R.layout.main
     */
    int value();
}
