package com.dfst.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yanfei on 2016-12-14.
 */
public class ScrollSupportedViewPager extends ViewPager {

    private boolean canScroll = true;

    public ScrollSupportedViewPager(Context context) {
        super(context);
    }

    public ScrollSupportedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (canScroll)
            return super.onTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (canScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }
}
