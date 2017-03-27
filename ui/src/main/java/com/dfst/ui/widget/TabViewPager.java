package com.dfst.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfei on 2016-10-27.
 */
class TabViewPager extends ViewPager {
    private Direction direction;
    private PageItem[] tabs;
    private List<AnimatorSet> animatorSetList = new ArrayList<>();

    private float touchDownX;

    public TabViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    public Direction currentDirection() {
        return  direction;
    }

    public void clearAnimations() {
        for (AnimatorSet set : animatorSetList) {
            set.cancel();
        }
        animatorSetList.clear();
    }

    public void setTabItems(PageItem[] tabs) {
        this.tabs = tabs;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (ev.getX() > touchDownX) {
                    direction = Direction.RIGHT;
                } else if (ev.getX() < touchDownX) {
                    direction = Direction.LEFT;
                } else {
                    direction = Direction.IDLE;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public enum Direction {
        RIGHT,
        LEFT,
        IDLE
    }

    private TabScroller mScroller = null;

    private void postInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new TabScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    class TabScroller extends Scroller {

        public TabScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            int current = getCurrentItem();
            int previous = dx > 0 ? current - 1 : current + 1;
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(tabs[previous].getCheckedView(), "alpha", tabs[previous].getCheckedView().getAlpha(), 0f),
                    ObjectAnimator.ofFloat(tabs[previous].getUnCheckedView(), "alpha", tabs[previous].getUnCheckedView().getAlpha(), 1f),
                    ObjectAnimator.ofFloat(tabs[current].getCheckedView(), "alpha", tabs[current].getCheckedView().getAlpha(), 1f),
                    ObjectAnimator.ofFloat(tabs[current].getUnCheckedView(), "alpha", tabs[current].getUnCheckedView().getAlpha(), 0f));
            animatorSet.setDuration(duration);
            animatorSet.start();
            animatorSetList.add(animatorSet);
            /*ObjectAnimator.ofFloat(tabs[previous].getCheckedView(), "alpha", tabs[previous].getCheckedView().getAlpha(), 0f).setDuration(duration).start();
            ObjectAnimator.ofFloat(tabs[previous].getUnCheckedView(), "alpha", tabs[previous].getUnCheckedView().getAlpha(), 1f).setDuration(duration).start();
            ObjectAnimator.ofFloat(tabs[current].getCheckedView(), "alpha", tabs[current].getCheckedView().getAlpha(), 1f).setDuration(duration).start();
            ObjectAnimator.ofFloat(tabs[current].getUnCheckedView(), "alpha", tabs[current].getUnCheckedView().getAlpha(), 0f).setDuration(duration).start();*/
            super.startScroll(startX, startY, dx, dy, duration);
        }

    }
}
