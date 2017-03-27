package com.dfst.core.util;

/**
 * @author yanfei@supcon.com
 * Class 操作工具
 * @date 2016/3/15.
 */
public class ClassUtil {
    /**
     * 功能同JDK的Class.forName(String)方法，
     * 不同的是class not found时不再抛出异常
     * 而是返回null
     * @param name class name
     * @return Class 实例
     */
    public static Class<?> forName(String name) {
        return forNmae(name, null);
    }

    /**
     * JDK的Class.forName(String)方法的升级版，可指定找不到
     * 该类是的默认返回值
     * @param name class name
     * @param defaultValue 默认返回值
     * @return Class 实例
     */
    private static Class<?> forNmae(String name, Class<?> defaultValue) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 获取给定参数集的Type类型。
     * @param args 参数集
     * @return Type数组
     */
    public static Class<?>[] agrsTypes(Object... args) {
        int length = args.length;
        Class<?>[] argsTypes = new Class<?>[length];
        for (int i = 0; i < length; i++) {
            argsTypes[i] = args[i].getClass();
        }
        return argsTypes;
    }
}
