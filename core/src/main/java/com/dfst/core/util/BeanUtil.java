package com.dfst.core.util;


import com.dfst.core.base.BeanDefinition;
import com.dfst.core.base.DefaultBeanFactory;

/**
 * @author yanfei@supcon.com
 * Bean 操作了
 * @date 2016/3/17.
 */
public class BeanUtil {
    /**
     * 清除容器中name命名的bean，释放内存
     * @param name bean 定义名称
     */
    public static void release(String name) {
        if (DefaultBeanFactory.customDefinitionMap.containsKey(name)) {
            BeanDefinition beanDefinition = DefaultBeanFactory.customDefinitionMap.get(name);
            if (beanDefinition != null) {
                DefaultBeanFactory.customBeanMap.remove(beanDefinition);
            }
        }
    }
}
