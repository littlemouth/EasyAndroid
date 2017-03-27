package com.dfst.core.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by yanfei on 2016/3/16.
 */
public class ResourcesUtil {
    public static String getString(Context context, int resId) {
        try {
            return context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getResource(Context context, int resId, Class<?> clazz) {
        try {
            if (clazz == String.class) {
                return context.getResources().getString(resId);
            } else if (clazz == String[].class) {
                return context.getResources().getStringArray(resId);
            } else if (clazz == Integer.class || clazz == int.class) {
                return context.getResources().getInteger(resId);
            } else if (clazz == Integer[].class || clazz == int[].class) {
                return context.getResources().getIntArray(resId);
            } else {
                return null;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
