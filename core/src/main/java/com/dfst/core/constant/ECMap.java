package com.dfst.core.constant;

import com.dfst.core.listener.OnClick;
import com.dfst.core.listener.OnItemClick;
import com.dfst.core.listener.OnListener;
import com.dfst.core.listener.OnLongClick;
import com.dfst.core.listener.OnRadioGroupCheckedChanged;
import com.dfst.core.listener.OnTouch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanfei@supcon.com
 * 该类保存enum到class的映射
 * @date 2016/3/16.
 */
public final class ECMap {
    /** 事件枚举到事件类的映射 */
    public static final Map<ListenerType, Class<? extends OnListener>> listenerMap =
            new HashMap<ListenerType, Class<? extends OnListener>>();
    public static final Map<Class<?>, String> ResourceMap =
            new HashMap<Class<?>, String>();

    static {
        listenerMap.put(ListenerType.Click, OnClick.class);
        listenerMap.put(ListenerType.LongClick, OnLongClick.class);
        listenerMap.put(ListenerType.Touch, OnTouch.class);
        listenerMap.put(ListenerType.ItemClick, OnItemClick.class);
        listenerMap.put(ListenerType.RadioGroupCheckedChanged, OnRadioGroupCheckedChanged.class);

        ResourceMap.put(String.class, "string");
        ResourceMap.put(String[].class, "array");
        ResourceMap.put(Integer.class, "integer");
        ResourceMap.put(Integer[].class, "array");
        ResourceMap.put(int.class, "integer");
        ResourceMap.put(int[].class, "array");
    }
}
