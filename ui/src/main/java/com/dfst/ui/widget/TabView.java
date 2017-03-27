package com.dfst.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfst.core.util.DensityUtil;
import com.dfst.ui.R;

/**
 * Created by littlemouth on 16-11-26.
 */
public class TabView extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final int LABELS_UP = 0;
    public static final int LABELS_BOTTOM = 1;

    private Context mContext;
    private TextView[] labels;
    private Options mOptions;

    private LinearLayout labelsLayout;
    private ScrollSupportedViewPager viewPager;
    private TabFragmentPageAdapter adapter;
    private CurrentView currentView;

    private int size;
    private int currentPosition;
    private int labelPadding;
    private int currentViewHeight;

    private float downX;
    private int flag; // 0:未滑动, 1:next, -1:prevoips
    private int currentViewStart;
    private boolean mCanScroll = true;
    private FragmentManager fragmentManager;

    public TabView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        labelPadding = DensityUtil.dip2px(mContext, 10);
        currentViewHeight = DensityUtil.dip2px(mContext, 5);
    }

    public void build(Options options) {
        if (options != null) {
            mOptions = options;
            if (mOptions.tabs == null && mOptions.tabs.length == 0) return;
            if (mOptions.labels == null && mOptions.labels.length == 0) return;
            if (mOptions.tabs.length != mOptions.labels.length) return;

            mCanScroll = mOptions.canScroll;
            size = options.tabs.length;
            labels = new TextView[size];
            currentPosition = mOptions.defaultPosition;
            LayoutParams labelsParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            labelsLayout = new LinearLayout(mContext);
            labelsLayout.setLayoutParams(labelsParams);
            
            for (int i = 0; i < size; i++) {
                LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                TextView label = new TextView(mContext);
                label.setPadding(labelPadding, labelPadding, labelPadding, labelPadding);
                label.setText(mOptions.labels[i]);
                label.setGravity(Gravity.CENTER);
                if (mOptions.labelTextColor != null)
                    label.setTextColor(mOptions.labelTextColor);
                if (mOptions.labelsTextSize != null)
                    label.setTextSize(mOptions.labelsTextSize);
                label.setLayoutParams(params);
                label.setOnClickListener(this);
                label.setTag(i);
                labelsLayout.addView(label);
                if (i == currentPosition)
                    label.setTextColor(mOptions.currentLabelColor);
                labels[i] = label;
            }


            LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
            contentParams.weight = 1;
            viewPager = new ScrollSupportedViewPager(mContext);
            viewPager.setId(R.id.tab_view_viewpager_ea_dfst);
            viewPager.setLayoutParams(contentParams);
            fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            adapter = new TabFragmentPageAdapter(fragmentManager, mOptions.tabs);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(currentPosition);
            viewPager.addOnPageChangeListener(this);
            viewPager.setCanScroll(mCanScroll);
            if (mOptions.offscreenPageLimit != null)
                viewPager.setOffscreenPageLimit(mOptions.offscreenPageLimit);

            if (mOptions.currentViewHeight != null)
                currentViewHeight = mOptions.currentViewHeight;
            LayoutParams currentParams = new LayoutParams(LayoutParams.MATCH_PARENT, currentViewHeight);
            currentView = new CurrentView(mContext, mOptions.currentLabelColor);
            currentView.setLayoutParams(currentParams);

            if (mOptions.labelBackgroundColor != null) {
                labelsLayout.setBackgroundColor(mOptions.labelBackgroundColor);
                currentView.setBackgroundColor(mOptions.labelBackgroundColor);
            }

            if (mOptions.labelsPosition == LABELS_UP) {
                addView(labelsLayout);
                addView(currentView);
                addView(viewPager);
            }  else {
                addView(viewPager);
                addView(currentView);
                addView(labelsLayout);
            }
        }
    }

    public void setCurrentPosition(int position) {
        if (position >= size) {
            viewPager.setCurrentItem(position, mCanScroll);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dis = ev.getX() - downX;
                if (currentPosition > 0 && dis > 0) {
                    flag = -1;
                } else if (currentPosition < size - 1 && dis < 0) {
                    flag = 1;
                } else {
                    flag = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (position != currentPosition) {
            flag = position > currentPosition ? 1 : -1;
            viewPager.setCurrentItem(position, mCanScroll);

            if (!mCanScroll) {
                currentViewStart = getWidth() / size * position;
                currentView.invalidate();
            }
            currentPosition = position;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.i("Test", "positionOffsetPixels  -----> " + position + "   " + positionOffsetPixels);
        if (positionOffsetPixels == 0) return;
        if (flag == 1) {
            currentViewStart = positionOffsetPixels / size + getWidth() / size * position;
        } else if (flag == -1) {
            currentViewStart = (positionOffsetPixels - getWidth()) / size  + getWidth() / size * (position + 1);
        }
        currentView.invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (mOptions != null && mOptions.currentLabelColor != null) {
            labels[currentPosition].setTextColor(mOptions.labelTextColor);
            labels[position].setTextColor(mOptions.currentLabelColor);
        }
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            currentViewStart = currentPosition * getWidth() / size;
            currentView.invalidate();
        }
    }

    private class TabFragmentPageAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;
        public TabFragmentPageAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

    private class CurrentView extends View {
        private Paint paint;
        private float currentStart, currentEnd;

        public CurrentView(Context context, int paintColor) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(paintColor);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = getWidth()  / size;
            int height = getHeight();
            currentStart = currentViewStart;
            currentEnd = currentStart + width;
            canvas.drawRect(currentStart, 0, currentEnd, height, paint);
        }

    }

    public static class Options {
        public String[] labels;
        public Fragment[] tabs;
        public Integer labelsPosition = LABELS_UP;
        public Integer labelsTextSize;
        public Integer labelTextColor = Color.BLACK;
        public Integer currentLabelColor = Color.BLUE;
        public Integer defaultPosition = 0;
        public Integer currentViewHeight;
        public Integer labelBackgroundColor;
        public Integer offscreenPageLimit;
        public boolean canScroll = true;
    }

}
