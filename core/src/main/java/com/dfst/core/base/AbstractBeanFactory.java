package com.dfst.core.base;

/**
 * @author yanfei@supcon.com
 * Bean工厂抽象类，声明了一些Bean、BeanDefinition操作的方法
 * 实现了父类的getBean(String name) 方法
 * @date 2016/3/15.
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    /**
     * 根据 名称 获取bean定义
     * @param name bean 名称
     * @param clazz bean class
     * @return
     */
    public abstract BeanDefinition getBeanDefinition(String name, Class<?> clazz);

    /**
     * 注册 bean 定义
     * @param clazz bean class
     * @return BeanDefinition 实例
     */
    public abstract BeanDefinition registerBeanDefinition(Class<?> clazz);

    /**
     * 判断缓存中是否有该bean定义注册的bean实例缓存
     * @param beanDefinition bean 定义
     * @return
     */
    public abstract boolean containsBeanCache(BeanDefinition beanDefinition);

    /**
     * 判 根据 bean 定义 获得bean实例的缓存
     * @param beanDefinition
     * @return bean 实例
     */
    public abstract Object getBeanCache(BeanDefinition beanDefinition);

    /**
     * 根据给定bean定义创建bean实例
     * @param beanDefinition
     * @return bean 实例
     */
    public abstract Object createBean(BeanDefinition beanDefinition);

    /**
     * 缓存给定bean实例
     * @param beanDefinition
     * @param obj 需要缓存的实例
     */
    public abstract void registerBeanCache(BeanDefinition beanDefinition, Object obj);

    /**
     * 根据bean注册名称获取bean实例
     * @param name bean 名称
     * @param clazz bean class
     * @return
     */
    @Override
    public final Object getBean(String name, Class<?> clazz) {
        BeanDefinition beanDefinition = getBeanDefinition(name, clazz);
        if (beanDefinition == null) {
            return null;
        }
        if (beanDefinition.isSingleton()) {
            if (containsBeanCache(beanDefinition)) {
                return getBeanCache(beanDefinition);
            }
            Object obj = createBean(beanDefinition);
            registerBeanCache(beanDefinition, obj);
            return  obj;
        }

        return createBean(beanDefinition);
    }

}
