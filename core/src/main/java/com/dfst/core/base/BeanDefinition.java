package com.dfst.core.base;

/**
 * @author yanfei@supcon.com
 * Bean 定义类
 * @date 2016/3/15.
 */
public class BeanDefinition {
    /** bean 是否是单例， 默认为否 */
    private boolean singleton = false;
    /** bean 的类型 */
    private Class<?> type;

    private boolean platformBD;

    public boolean isPlatformBD() {
        return platformBD;
    }

    public void setPlatformBD(boolean platformBD) {
        this.platformBD = platformBD;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}
