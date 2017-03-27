package com.dfst.core.base;

/**
 * @author yanfei@supcon.com
 * Bean 工厂接口
 * @date 2016/3/15.
 */
public interface BeanFactory {
    /**
     * 根据bean注册名称获取bean实例
     * @param name
     * @return bean 实例
     */
    public Object getBean(String name, Class<?> clazz);
}
