package com.dfst.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfst.core.util.Constants;
import com.dfst.core.util.DensityUtil;
import com.dfst.ui.R;

/**
 * Tab View
 * @author  yanfei.
 * @date 2016-10-26
 */
public class PageView extends LinearLayout {

    private Context mContext;
    private FrameLayout titleLayout;
    private ViewPager viewPager;
    private LinearLayout innerTabLayout, outerTabLayout;
    private View tabDevider;
    private int titleHeight, tabHeight;
    private View customsTitleView;

    //private Fragment mOptions.pages[];
    private TabItem tabs[];
    private int size;
    private int currentPosition;

    private Options mOptions;

    private OnSelectChangedListener onSelectChangedListener;

    public PageView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        initDimension();
        initView();
    }

    private void initDimension() {
        titleHeight = DensityUtil.dip2px(mContext, 50);
        tabHeight = DensityUtil.dip2px(mContext, 55);
    }

    private void initView() {
        View.inflate(mContext, R.layout.activity_titled_activity_ea_dfst, this);
        setOrientation(VERTICAL);
        // title
        titleLayout = (FrameLayout) findViewById(R.id.tab_title_framelayout_ea_dfst);
        titleLayout.setVisibility(GONE);

        // view pager
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager_ea_dfst);

        // tab
        outerTabLayout = (LinearLayout) findViewById(R.id.tab_button_outer_linearlayout_ea_dfst);

        innerTabLayout = (LinearLayout) findViewById(R.id.tab_button_linearlayout_ea_dfst);
        //innerTabLayout.setPadding(0, DensityUtil.dip2px(mContext, 3), 0, DensityUtil.dip2px(mContext, 3));

        tabDevider = findViewById(R.id.tab_divide_ea_dfst);
    }

    private void createTabView() {

        // create pages
        if (mContext instanceof FragmentActivity) {
            FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            viewPager.setAdapter(new TabFragmentPageAdapter(fm, mOptions.pages));
            viewPager.addOnPageChangeListener(new TabOnPageChangeListener());
            viewPager.setCurrentItem(currentPosition);
        } else {
            Log.e(Constants.TAG, "Your Activity must extend FragmentActivity");
        }

        // create tab
        tabs = new TabItem[size];
        for (int index = 0; index < size; index++) {
            TabItem item = new TabItem(mContext);
            item.setTag(index);
            item.setImageResource(mOptions.checkedIcons[index], mOptions.unCheckedIcons[index]);
            if (mOptions.labels != null) {
                if (mOptions.unCheckedLabels != null) {
                    item.setLabelText(mOptions.labels[index], mOptions.unCheckedLabels[index]);
                } else {
                    item.setLabelText(mOptions.labels[index], mOptions.labels[index]);
                }

                if (mOptions.checkedLabelColor != null) {
                    item.setCheckedLabelTextColor(mOptions.checkedLabelColor);
                }

                if (mOptions.unCheckedLabelColor != null) {
                    item.setUnCheckedLabelTextColor(mOptions.unCheckedLabelColor);
                }

                if (mOptions.labelTextSize != null) {
                    item.setLabelTextSize(mOptions.labelTextSize);
                }
            }

            if (mOptions.checkedItemBackgroundColor != null) {
                item.setCheckedItemBackGroundColor(mOptions.checkedItemBackgroundColor);
            }

            if (mOptions.unCheckedItemBackgroundColor != null) {
                item.setUnCheckedItemBackGroundColor(mOptions.unCheckedItemBackgroundColor);
            }

            if (currentPosition == index) {
                item.setChecked(true);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (currentPosition != position) {
                        viewPager.setCurrentItem(position, false);
                    }
                }
            });
            innerTabLayout.addView(item);
            tabs[index] = item;
        }
    }

    public void init(Options options) {
        this.mOptions = options;
        if (mOptions == null) {
            Log.e(Constants.TAG, "Make sure 'options' has been set");
            return;
        }

        if (mOptions.pages == null) {
            Log.e(Constants.TAG, "Make sure 'pages' has been set");
            return;
        }

        size = mOptions.pages.length;

        if (mOptions.checkedIcons == null || mOptions.unCheckedIcons == null) {
            Log.e(Constants.TAG, "Make sure 'CheckedIcons' or 'UnCheckedIcons' has been set");
            return;
        }

        if (!(size == mOptions.checkedIcons.length
                && size == mOptions.unCheckedIcons.length)) {
            Log.e(Constants.TAG, "Check length of 'page', 'checkedIcons', 'unCheckedIcons'");
            return;
        }

        if ((mOptions.labels != null && size != mOptions.labels.length)) {
            Log.e(Constants.TAG, "Check length of 'labels'");
            return;
        }

        currentPosition = options.defaultPosition;
        createTabView();

    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public int getTabHeight() {
        return tabHeight;
    }

    public void setTabHeight(int tabHeight) {
        this.tabHeight = tabHeight;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) outerTabLayout.getLayoutParams();
        params.height = tabHeight;
    }

    /**
     * 自定义标题栏
     * @param layoutId 自定义标题栏布局id
     */
    public void setTitleLayout(int layoutId) {
        inflate(mContext, layoutId, titleLayout);
        titleLayout.setVisibility(VISIBLE);
    }

    /**
     * 自定义标题栏
     * @param view 自定义标题栏
     */
    public void setTitleView(View view) {
        customsTitleView = view;
        innerTabLayout.removeAllViews();
        titleLayout.addView(customsTitleView);
        titleLayout.setVisibility(VISIBLE);
    }

    /**
     * 设置tab背景色
     * @param color
     */
    public void setTabBackgroundColor(int color) {
        innerTabLayout.setBackgroundColor(color);
    }

    public void setTabBackgroundResource(int resId) {
        innerTabLayout.setBackgroundResource(resId);
    }

    /**
     * 设置tab bar分隔线宽度
     * @param height
     */
    public void setTabDeviderHeight(float height) {
        LinearLayout.LayoutParams params = (LayoutParams) tabDevider.getLayoutParams();
        params.height = DensityUtil.dip2px(mContext, height);
    }

    public void setOffscreenPageLimit(int limit) {
        if (viewPager != null)
            viewPager.setOffscreenPageLimit(limit);
    }

    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        this.onSelectChangedListener = listener;
    }

    class TabItem extends FrameLayout{
        private LinearLayout checkedLayout, unCheckedLayout;
        private ImageView checkedIcon, unCheckedIcon;
        private TextView label, unCheckedLabel;

        private boolean checked;

        private Context mContext;

        public TabItem(Context context) {
            super(context);
            mContext = context;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            setLayoutParams(params);

            /** checked layout **/
            FrameLayout.LayoutParams checkedParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            checkedLayout = new LinearLayout(context);
            checkedLayout.setLayoutParams(checkedParams);
            checkedLayout.setOrientation(LinearLayout.VERTICAL);
            checkedLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0);
            imageParams.weight = 1;
            imageParams.topMargin = DensityUtil.dip2px(context, 5);
            checkedIcon = new ImageView(context);
            checkedIcon.setLayoutParams(imageParams);
            checkedIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            checkedLayout.addView(checkedIcon);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //textParams.weight = 2;
            textParams.bottomMargin = DensityUtil.dip2px(context, 5);
            label = new TextView(context);
            label.setLayoutParams(textParams);
            label.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            label.setTextSize(11);
            checkedLayout.addView(label);
            checkedLayout.setAlpha(0f);
            if (mOptions.labels == null && mOptions.unCheckedLabels == null) {
                label.setVisibility(GONE);
                imageParams.bottomMargin = DensityUtil.dip2px(context, 5);
            }
            addView(checkedLayout);

            /** unchecked layout **/
            FrameLayout.LayoutParams unCheckedParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            unCheckedLayout = new LinearLayout(context);
            unCheckedLayout.setLayoutParams(unCheckedParams);
            unCheckedLayout.setOrientation(LinearLayout.VERTICAL);
            unCheckedLayout.setGravity(Gravity.CENTER);


            LinearLayout.LayoutParams unImageParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0);
            unImageParams.weight = 1;
            unImageParams.topMargin = DensityUtil.dip2px(context, 5);
            unCheckedIcon = new ImageView(context);
            unCheckedIcon.setLayoutParams(unImageParams);
            unCheckedIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            unCheckedLayout.addView(unCheckedIcon);

            LinearLayout.LayoutParams unTextParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //unTextParams.weight = 2;
            unTextParams.bottomMargin = DensityUtil.dip2px(context, 5);
            unCheckedLabel = new TextView(context);
            unCheckedLabel.setLayoutParams(unTextParams);
            unCheckedLabel.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            unCheckedLabel.setTextSize(11);
            unCheckedLayout.addView(unCheckedLabel);
            if (mOptions.labels == null && mOptions.unCheckedLabels == null) {
                unCheckedLabel.setVisibility(GONE);
                unImageParams.bottomMargin = DensityUtil.dip2px(context, 5);
            }

            addView(unCheckedLayout);
        }

        public void setCheckedItemBackGroundColor(int checkedColor) {
            try {
                checkedLayout.setBackgroundColor(ContextCompat.getColor(mContext, checkedColor));
            } catch (RuntimeException e) {
                checkedLayout.setBackgroundColor(checkedColor);
            }
        }

        public void setUnCheckedItemBackGroundColor(int unCheckedColor) {
            try {
                unCheckedLayout.setBackgroundColor(ContextCompat.getColor(mContext, unCheckedColor));
            } catch (RuntimeException e) {
                unCheckedLayout.setBackgroundColor(unCheckedColor);
            }
        }

        public void setImageResource(int checkedResId, int unCheckedResId) {
            checkedIcon.setImageResource(checkedResId);
            unCheckedIcon.setImageResource(unCheckedResId);
            /*int w = View.MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            checkedIcon.measure(w, h);
            int width = checkedIcon.getMeasuredWidth();

            FrameLayout.LayoutParams promptParams = new FrameLayout.LayoutParams(promptWidth, promptWidth);
            promptParams.leftMargin = (int) (width * 0.6f);
            promptParams.topMargin = DensityUtil.dip2px(itemContext, 3);
            promptParams.gravity = Gravity.CENTER_HORIZONTAL;
            prompt.setLayoutParams(promptParams);*/
        }

        public void setLabelText(String checkedText, String unCheckedText) {
            label.setText(checkedText);
            unCheckedLabel.setText(unCheckedText);
        }

        public void setCheckedLabelTextColor(int checkedColor) {
            try {
                label.setTextColor(ContextCompat.getColor(mContext, checkedColor));
            } catch (RuntimeException e) {
                label.setTextColor(checkedColor);
            }
        }

        public void setUnCheckedLabelTextColor(int unCheckedColor) {
            try {
                unCheckedLabel.setTextColor(ContextCompat.getColor(mContext, unCheckedColor));
            } catch (RuntimeException e) {
                unCheckedLabel.setTextColor(unCheckedColor);
            }
        }

        public void setLabelTextSize(int size) {
            unCheckedLabel.setTextSize(size);
            label.setTextSize(size);
        }

        public void setAlpha(float checkedAlpha, float unCheckAlpha) {
            checkedLayout.setAlpha(checkedAlpha);
            unCheckedLayout.setAlpha(unCheckAlpha);
        }

        public void setLabelVisibility(int visibly) {
            label.setVisibility(visibly);
            unCheckedLabel.setVisibility(visibly);
        }

        public View getCheckedView() {
            return checkedLayout;
        }

        public View getUnCheckedView() {
            return unCheckedLayout;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
            if (checked) {
                setAlpha(1f, 0f);
            } else {
                setAlpha(0f, 1f);
            }
        }

        public boolean isChecked() {
            return checked;
        }
    }

    private class Prompt extends View {

        private Paint paint;
        private int count;
        private int textSize;
        private int redColor;

        public Prompt(Context context) {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setFakeBoldText(true);
            textSize = DensityUtil.dip2px(context, 10);
            redColor = Color.RED;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (count > 0) {
                setVisibility(VISIBLE);
                int height = getHeight();
                int width = getWidth();
                int radius = height > width ? width / 2 : height / 2;
                paint.setColor(redColor);
                canvas.drawCircle(width * 0.5f, height * 0.5f, radius, paint);

                paint.setTextSize(textSize);
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                // baseline
                int baseline = (height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                paint.setColor(Color.WHITE);
                canvas.drawText(String.valueOf(count), width * 0.5f, baseline, paint);
            } else {
                setVisibility(GONE);
            }
            super.onDraw(canvas);
        }

        public void setCount(int count) {
            if (count < 0)
                count = 0;
            this.count = count;
            setVisibility(VISIBLE);
            invalidate();
        }

        public int getCount() {
            return this.count;
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

    private class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tabs[position].setChecked(true);
            tabs[currentPosition].setChecked(false);
            currentPosition = position;
            if (onSelectChangedListener != null)
                onSelectChangedListener.onSelectedChanged(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public static class Options {
        public Fragment[] pages;
        public int checkedIcons[];
        public int unCheckedIcons[];
        public String labels[];
        public String unCheckedLabels[];
        public Integer checkedLabelColor;
        public Integer unCheckedLabelColor;
        public int defaultPosition;
        public Integer checkedItemBackgroundColor;
        public Integer unCheckedItemBackgroundColor;
        public Integer labelTextSize;
    }

    public interface OnSelectChangedListener {
        void onSelectedChanged(int position);
    }
}
