package com.dfst.core.util;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * View 工具类
 * @date 2016/3/15.
 */
public class ViewUtil {
    /** 资源类型map */
    private static final Map<String, Object> Type_Map_Resouce_Class = new HashMap<String, Object>();
    private static Class<?> resource_class = null;

    public static void setApplication(Application app) {
        resource_class = ClassUtil.forName(app.getPackageName() + ".R");
    }

    public static int getResourceId(String type, String name) {
        Object resource = Type_Map_Resouce_Class.get(type);
        if (resource == null) {
            resource = ClassUtil.forName(resource_class.getName() + "$" + type);
            if (resource != null) {
                Type_Map_Resouce_Class.put(type, resource);
            }
        }
        Object obj = ReflectUtil.get(resource, name);
        return obj == null ? 0 : (int) obj;
    }
}
