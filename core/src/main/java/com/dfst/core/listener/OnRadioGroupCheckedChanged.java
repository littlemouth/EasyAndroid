package com.dfst.core.listener;

import android.view.View;
import android.widget.RadioGroup;

/**
 * @author yanfei@supcon.com
 * RadioGroup checked changed 事件类
 * 注解 @AZListener 中参数 type 值之一。
 * Created by yanfei on 2016/5/9.
 */
public class OnRadioGroupCheckedChanged extends OnListener implements RadioGroup.OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        invoke(group, checkedId);
    }

    @Override
    public void listener(View view) {
        ((RadioGroup) view).setOnCheckedChangeListener(this);
    }
}
