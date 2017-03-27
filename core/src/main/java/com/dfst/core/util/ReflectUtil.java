package com.dfst.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanfei@supcon.com
 * 反射 操作工具
 * @date 2016/3/15.
 */
public class ReflectUtil {
    /**
     * 反射获得字段值
     * @param obj 字段持有对象
     * @param name 字段名
     * @return 字段值
     */
    public static Object get(Object obj, String name) {
        Object returnValue = null;
        try {
            Field field = null;
            if (obj instanceof Class) {
                field = ((Class)obj).getDeclaredField(name);
            } else {
                field = obj.getClass().getDeclaredField(name);
            }
            field.setAccessible(true);
            returnValue = field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return returnValue;
        }
    }

    /**
     * 反射获得method
     * @param obj 方法持有对象
     * @param methodName 方法名
     * @param args 方法参数类型集
     * @return 目标方法
     */
    public static Method getMethod(Object obj, String methodName, Class<?>... args) {
        Method method = null;
        try {
            if (obj instanceof  Class) {
                method = ((Class)obj).getDeclaredMethod(methodName, args);
            } else {
                method = obj.getClass().getDeclaredMethod(methodName, args);
            }
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
