package com.dfst.core.base;

import android.app.Fragment;
import android.content.Context;

import com.dfst.core.annotation.AView;
import com.dfst.core.annotation.After;
import com.dfst.core.annotation.Bean;
import com.dfst.core.annotation.Before;
import com.dfst.core.annotation.Layout;
import com.dfst.core.annotation.Listener;
import com.dfst.core.annotation.Resource;
import com.dfst.core.constant.ECMap;
import com.dfst.core.exception.EntityNotFoundException;
import com.dfst.core.exception.ResourceNotFoundException;
import com.dfst.core.exception.ViewNotFoundException;
import com.dfst.core.listener.OnListener;
import com.dfst.core.util.ResourcesUtil;
import com.dfst.core.util.ViewUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yanfei@supcon.com
 * 注入核心类。
 * 该类实现所有注解标示Field、Method、Type的自动注入
 * @date 2016/3/15.
 */
public class InjectCore {
    /**
     * fragment annotations 入口
     * @param target 目标fragment
     * @param root fragment 根view
     */
    public static void inject(Object target, android.view.View root) {
        // step 1  inject resource
        injectResources(target);
        // step 2  inject entity
        injectEntity(target);
        // step 3 inject view
        injectView(target, root);
        // step 4 inject listener
        injectListener(target, root);
    }

    /**
     * activity 布局注入
     * @param target 目标activity
     */
    public static void injectContentView(Object target) {
        Class<?> clazz = target.getClass();
        Layout annotation =
                clazz.getAnnotation(Layout.class);
        if (annotation != null) {
            int layoutId = annotation.value();
            ((android.app.Activity) target).setContentView(layoutId);

        }
    }

    /**
     * view 注入
     * @param target 目标 activity  or  fragment
     * @param root fragment 根view;
     */
    public static void injectView(Object target, android.view.View root) {
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            AView annotation = field.getAnnotation(AView.class);
            if (annotation != null) {
                try {
                    android.view.View view = null;
                    int viewId = annotation.value();
                    if (viewId == 0) {
                        viewId = ViewUtil.getResourceId("id", field.getName());
                        if (viewId == 0) {
                            Constructor<?> constructor = field.getType().getConstructor(Context.class);
                            Object obj = null;
                            if (target instanceof android.app.Activity) {
                                obj = constructor.newInstance(target);
                            } else {
                                if (target instanceof  Fragment)
                                    obj = constructor.newInstance(((Fragment) target).getActivity());
                                else
                                    obj = constructor.newInstance(((android.support.v4.app.Fragment) target).getActivity());
                            }
                            field.setAccessible(true);
                            field.set(target, obj);
                            continue;
                        }
                    }
                    if (root == null) {
                        view = ((android.app.Activity)target).findViewById(viewId);
                    } else {
                        view = root.findViewById(viewId);
                    }

                    if (view == null) {
                        throw new ViewNotFoundException(field.getName());
                    }

                    field.setAccessible(true);
                    field.set(target, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ViewNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 注入 entity
     * @param target 目标对象
     */
    public static void injectEntity(Object target) {
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Bean annotation = field.getAnnotation(Bean.class);
            if (annotation != null) {
                try {
                    Class<?> cls = field.getType();
                    String name = annotation.name();
                    Object obj = DefaultBeanFactory.getFactory().getBean(name, cls);
                    if (obj == null) {
                        DefaultBeanFactory.getFactory().registerBeanDefinition(cls);
                        obj = DefaultBeanFactory.getFactory().getBean(name, cls);
                        if (obj == null) {
                            throw new EntityNotFoundException(name);

                        }
                    }
                    field.setAccessible(true);
                    field.set(target, obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (EntityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 事件注入
     * @param target 目标 activity   or   fragment
     * @param root  fragment 根view
     */
    public static void injectListener(Object target, android.view.View root) {
        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Listener listener = method.getAnnotation(Listener.class);

            if (listener != null) {
                int[] viewIds = listener.value();
                Class<? extends OnListener> listenerType = ECMap.listenerMap.get(listener.type());
                for (int viewId : viewIds) {
                    android.view.View view = null;
                    if (root == null) {
                        view = ((android.app.Activity) target).findViewById(viewId);
                    } else {
                        view = root.findViewById(viewId);
                    }

                    try {
                        method.setAccessible(true);
                        OnListener onListener = listenerType.newInstance();
                        onListener.listener(view, target, method);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 调用activity添加了@AZBefore注解的方法，该方法在activity
     * 的onCreate方法前执行
     * @param target 目标activity
     */
    public static void beforeOnCreate(Object target) {
        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for ( Method method : methods) {
            Before before = method.getAnnotation(Before.class);
            if (before != null) {
                try {
                    method.setAccessible(true);
                    method.invoke(target);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调用activity添加了@AZAfter注解的方法，该方法在activity的
     * setContentView()后执行，该方法执行前activity中的view组件
     * 已经注入。该方法可用来为view设置初始状态
     * @param target
     */
    public static void afterOnCreate(Object target) {
        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for ( Method method : methods) {
            After after = method.getAnnotation(After.class);
            if (after != null) {
                try {
                    method.setAccessible(true);
                    method.invoke(target);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注入res目录下的资源
     * @param target 目标activity or fragment
     */
    public static void injectResources(Object target) {
        Class<?> clazz = target.getClass();
        Context context = null;
        if (target instanceof android.app.Activity) {
            context = (android.app.Activity) target;
        } else if (target instanceof Fragment) {
            context = ((Fragment) target).getActivity();
        } else {
            context = ((android.support.v4.app.Fragment) target).getActivity();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Resource azAndroidRes = field.getAnnotation(Resource.class);
                if (azAndroidRes != null) {
                    Class<?> type = field.getType();
                    String resType = ECMap.ResourceMap.get(type);
                    int resId = azAndroidRes.value();
                    if (resId == 0) {
                        resId = ViewUtil.getResourceId(resType, field.getName());
                    }

                    Object str = ResourcesUtil.getResource(context, resId, type);
                    if (str == null) {
                        throw new ResourceNotFoundException("R." + resType + "." + field.getName());
                    }
                    field.set(target, str);
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
