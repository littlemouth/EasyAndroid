package com.dfst.core.base;

import android.app.Application;

import com.dfst.core.app.InstrumentationBean;
import com.dfst.core.util.ViewUtil;

import java.lang.reflect.Field;

/**
 * @author yanfei@supcon.com
 * ioc 核心类
 * 该类中将主线程中的Instrumentation对象替换成自定义
 * Instrumentation对象。实现对Activity生命周期方法的
 * 监控
 * @date 2016/3/15.
 */
public class Ioc {
    /** 当前应用 Application 对象 */
    private Application application;
    /** Ioc 单例对象 */
    private static Ioc ioc;

    private Ioc() {};

    /**
     * 获得Ioc对象方法
     * @return Ioc 单例对象
     */
    public static Ioc getIoc() {
        if (ioc == null) {
            ioc = new Ioc();
        }
        return ioc;
    }


    public Application getApplication(Application app) {
        return app;
    }

    /**
     * 初始化框架
     * @param app 当前应用Application对象
     */
    public void init(Application app) {
        this.application = app;
        ViewUtil.setApplication(app);
        try {
            // 替换主线程中 的instrumentation 对象
            // start
            Field mainThreadField = application.getBaseContext().getClass().getDeclaredField("mMainThread");
            mainThreadField.setAccessible(true);
            Object mainThread = mainThreadField.get(application.getBaseContext());
            Field instrumentationField = mainThread.getClass().getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            InstrumentationBean instrumentation = new InstrumentationBean();

            copy(instrumentationField.get(mainThread), instrumentation);
            instrumentationField.set(mainThread, instrumentation);
            // end
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * copy 对象
     * @param obj 被copy对象
     * @param copy 目标对象
     */
    private void copy(final Object obj, final Object copy) {
        Class cls = obj.getClass();
        while (cls != null && cls != Object.class) {
            for (Field template : cls.getDeclaredFields()) {
                try {
                    Field field = cls.getDeclaredField(template.getName());
                    if (field != null && field.getType().isAssignableFrom(template.getType())) {
                        template.setAccessible(true);

                        field.set(copy, template.get(obj));


                    }
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

            cls = cls.getSuperclass();
        }
    }

}
