package com.dfst.core.base;

import com.dfst.core.annotation.Entity;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * 框架中默认bean工厂。
 * 默认工厂提供BeanDefinition、Bean容器，管理bean定义，
 * 并为单例bean提供缓存
 * @date 2016/3/15.
 */
public class DefaultBeanFactory extends AbstractBeanFactory
{
    /** custom BeanDefinition 容器 */
    public static final Map<String, BeanDefinition> customDefinitionMap = new Hashtable<String, BeanDefinition>();
    /**platform BeanDefinition 容器 */
    public static final Map<String, BeanDefinition> platformDefinitionMap = new Hashtable<String, BeanDefinition>();
    /** 平台Bean 容器 该容器用来缓存框架内的Bean*/
    public static final Map<BeanDefinition, BaseBean> platformBeanMap = new Hashtable<BeanDefinition, BaseBean>();
    /** 应用 Bean 容器 该容器用来缓存应用内的Bean，开发人员可清除容器内的bean*/
    public static final Map<BeanDefinition, Object> customBeanMap = new Hashtable<BeanDefinition, Object>();
    /** DefaultBeanFactory 单例对象 */
    private static DefaultBeanFactory factory;

    /**
     * 获得 DefaultBeanFactory 实例
     * @return
     */
    public static DefaultBeanFactory getFactory() {
        if (factory == null) {
            factory = new DefaultBeanFactory();
        }
        return factory;
    }

    /**
     * 根据 名称 获取bean定义
     * @param name bean 名称
     * @param clazz bean class
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String name, Class<?> clazz) {
        if (BaseBean.class.isAssignableFrom(clazz)) {
            return platformDefinitionMap.get(name);
        }
        return customDefinitionMap.get(name);
    }

    /**
     * 将给定类注册到bean定义容器中
     * @param clazz bean class
     * @return
     */
    @Override
    public BeanDefinition registerBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        Entity entity = clazz.getAnnotation(Entity.class);
        if (entity != null) {
            String name = entity.value();
            if (name.equals("")) {
                name = clazz.getSimpleName();
            }
            boolean singleton = entity.singleton();
            beanDefinition.setType(clazz);
            beanDefinition.setSingleton(singleton);
            if (BaseBean.class.isAssignableFrom(clazz)) {
                beanDefinition.setPlatformBD(true);
                platformDefinitionMap.put(name, beanDefinition);
            } else {
                customDefinitionMap.put(name, beanDefinition);
            }
        }
        return beanDefinition;
    }

    /**
     * 判断缓存中是否有该bean定义注册的bean实例缓存
     * @param beanDefinition bean 定义
     * @return
     */
    @Override
    public boolean containsBeanCache(BeanDefinition beanDefinition) {
        if (beanDefinition.isPlatformBD()) {
            if (platformBeanMap.containsKey(beanDefinition) && platformBeanMap.get(beanDefinition) != null)
                return true;
        } else {
            if (customBeanMap.containsKey(beanDefinition) && customBeanMap.get(beanDefinition) != null)
                return true;
        }
        return false;
    }

    /**
     * 根据bean定义获取缓存的bean实例
     * @param beanDefinition bean定义
     * @return 缓存中的bean实例
     */
    @Override
    public Object getBeanCache(BeanDefinition beanDefinition) {
        if (beanDefinition.isPlatformBD()) {
            return platformBeanMap.get(beanDefinition);
        }
        return customBeanMap.get(beanDefinition);
    }

    /**
     * 根据给定bean定义创建bean实例
     * @param beanDefinition
     * @return bean 实例
     */
    @Override
    public Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getType();
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将bean实例放入缓存
     * @param beanDefinition
     * @param obj 需要缓存的实例
     */
    @Override
    public void registerBeanCache(BeanDefinition beanDefinition, Object obj) {
        if (obj instanceof BaseBean) {
            platformBeanMap.put(beanDefinition, (BaseBean)obj);
        } else {
            customBeanMap.put(beanDefinition, obj);
        }
    }

}
