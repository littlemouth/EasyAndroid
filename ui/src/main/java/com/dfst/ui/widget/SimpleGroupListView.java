package com.dfst.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.dfst.core.util.DensityUtil;
import com.dfst.ui.adapter.GroupListAdapter;
import com.dfst.ui.adapter.SimpleGroupListAdapter;

/**
 * Created by yanfei on 2016-10-31.
 */
public class SimpleGroupListView extends ListView {
    private Context mContext;
    private GroupListAdapter adapter;
    private View topGroupView;
    private int translateY;

    private Paint paint;
    private int navigationBarWidth;
    private int navigationsPaddingRight;
    private int textPaddingTop, textPaddingBottom;
    private int nagativeBarTextSize, centerTextSize;
    private int centerTextBgRadius, centerTextCornerRadius;
    private boolean navigationBarActivated ;
    private int currentNavigationIndex;

    private List<String> navigations;

    private final OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (adapter == null) return;

            if (adapter.isGroup(firstVisibleItem)) {
                createTopGroupView(firstVisibleItem, firstVisibleItem, visibleItemCount);

            } else {
                int currentGroupPosition = findCurrentGroupPosition(firstVisibleItem);
                if (currentGroupPosition >= 0) {
                    createTopGroupView(currentGroupPosition, firstVisibleItem, visibleItemCount);
                } else {
                    topGroupView = null;
                }
            }

        }
    };

    public SimpleGroupListView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SimpleGroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawTopGroupView(canvas);
        drawNavigationBar(canvas);
        drawNavigationCenterText(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchDownPointInNavigationRect(ev.getX(), ev.getY())) {
                    //stopScroll(this);
                    navigationBarActivated = true;
                    navigateTo(ev.getY());
                    changeTo(navigations.get(currentNavigationIndex));
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (navigationBarActivated) {
                    navigateTo(ev.getY());
                    changeTo(navigations.get(currentNavigationIndex));
                    return true;
                }
            case MotionEvent.ACTION_UP:
                navigationBarActivated = false;
                invalidate();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * down event 是否发生在导航栏范围
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchDownPointInNavigationRect(float x, float y) {
        Rect rect = new Rect(getWidth() - navigationBarWidth, (int) (getHeight() * 0.1f),
                getWidth(), (int) (getHeight() * 0.9f));
        if (rect.contains((int) x, (int) y)) {
            return true;
        }
        return false;
    }

    /**
     * 绘制置顶组标题view
     * @param canvas
     */
    private void drawTopGroupView(Canvas canvas) {
        if (topGroupView != null) {
            canvas.save();
            canvas.translate(0, translateY);
            drawChild(canvas, topGroupView, getDrawingTime());
            canvas.restore();
        }
    }

    private void drawNavigationBar(Canvas canvas) {
        //paint.setColor(Color.LTGRAY);
        //paint.setAlpha(100);
        int left = getWidth() - navigationBarWidth;
        int right = getWidth();
        int top = (int)(getHeight() * 0.1f);
        int bottom = (int)(getHeight() * 0.9f);

        //Rect rect = new Rect(left, top, right, bottom);
        //canvas.drawRect(rect, paint);

        //paint.setAlpha(0);
        if (navigationBarActivated) {
            paint.setColor(Color.DKGRAY);
        } else {
            paint.setColor(Color.GRAY);
        }

        int textTop = top + textPaddingTop;
        int textBottom = bottom - textPaddingBottom;
        float space = (textBottom - textTop) * 1f / navigations.size();

        paint.setTextSize(nagativeBarTextSize);
        for (int i = 0; i < navigations.size(); i++) {
            //canvas.drawLine(left, textTop + space * i, right, textTop + space * i, paint);
            canvas.drawText(navigations.get(i), right - navigationsPaddingRight, textTop + space * (i + 1), paint);
        }
    }

    private void drawNavigationCenterText(Canvas canvas) {
        if (navigationBarActivated) {
            RectF bg = new RectF(getWidth() / 2 - centerTextBgRadius, getHeight() / 2 - centerTextBgRadius,
                    getWidth() / 2 + centerTextBgRadius, getHeight() / 2 + centerTextBgRadius);
            paint.setColor(Color.DKGRAY);
            paint.setAlpha(150);
            canvas.drawRoundRect(bg, centerTextCornerRadius, centerTextCornerRadius, paint);

            String text = navigations.get(currentNavigationIndex);
            paint.setColor(Color.WHITE);
            paint.setTextSize(centerTextSize);
            paint.setAlpha(255);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = (getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            if (currentNavigationIndex == 0)
                baseline -= DensityUtil.dip2px(mContext, 5);
            canvas.drawText(text, getWidth() / 2, baseline, paint);
        }
    }

    private int navigateTo(float y) {
        float textTop = getHeight() * 0.1f + textPaddingTop;
        float textBottom = getHeight() * 0.9f + textPaddingBottom;

        if (y < textTop) {
            currentNavigationIndex = 0;
        } else if (y > textBottom) {
            currentNavigationIndex = navigations.size() - 1;
        } else {
            float space = (textBottom - textTop) / navigations.size();
            currentNavigationIndex = (int) ((y - textTop) / space);
        }

        return  currentNavigationIndex;
    }

    private void init() {
        navigationBarWidth = DensityUtil.dip2px(mContext, 30);
        navigationsPaddingRight = DensityUtil.dip2px(mContext, 10);
        textPaddingBottom = DensityUtil.dip2px(mContext, 20);
        nagativeBarTextSize = DensityUtil.dip2px(mContext, 13);
        centerTextSize = DensityUtil.dip2px(mContext, 50);
        centerTextBgRadius = DensityUtil.dip2px(mContext, 40);
        centerTextCornerRadius = DensityUtil.dip2px(mContext, 5);
        textPaddingTop = textPaddingBottom;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        setOnScrollListener(mScrollListener);
        setVerticalScrollBarEnabled(false);
        setDividerHeight(0);

    }

    /**
     * 生成置顶组标题view
     * @param currentGroupPosition
     * @param firstVisiblePosition
     * @param visibleItemCount
     */
    private void createTopGroupView(int currentGroupPosition, int firstVisiblePosition, int visibleItemCount) {
        topGroupView = adapter.getView(currentGroupPosition, null, SimpleGroupListView.this);

        // read layout parameters
        LayoutParams layoutParams = (LayoutParams) topGroupView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (LayoutParams) generateDefaultLayoutParams();
            topGroupView.setLayoutParams(layoutParams);
        }

        int heightMode = MeasureSpec.getMode(layoutParams.height);
        int heightSize = MeasureSpec.getSize(layoutParams.height);

        if (heightMode == MeasureSpec.UNSPECIFIED) heightMode = MeasureSpec.EXACTLY;

        // measure & layout
        int ws = MeasureSpec.makeMeasureSpec(getWidth() - getListPaddingLeft() - getListPaddingRight(), MeasureSpec.EXACTLY);
        int hs = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        topGroupView.measure(ws, hs);

        topGroupView.layout(0, 0, topGroupView.getMeasuredWidth(), topGroupView.getMeasuredHeight());
        translateY = 0;

        int nextGroupPosition = findNextGroupPosition(firstVisiblePosition, visibleItemCount);
        if (nextGroupPosition >= 0 && nextGroupPosition - firstVisiblePosition == 1) {
            View nextGroupView = getChildAt(nextGroupPosition - firstVisiblePosition);
            int topGroupViewBottom = topGroupView.getBottom() + getPaddingTop();
            int nextGroupViewTop = nextGroupView.getTop();
            if (nextGroupViewTop - topGroupViewBottom < 0) {
                translateY = nextGroupViewTop - topGroupViewBottom;
            } else {
                translateY = 0;
            }
        }
    }

    /**
     * 获取当前分组标题view位置
     * @param position
     * @return
     */
    private int findCurrentGroupPosition(int position) {
        while (position >= 0) {
            if (isGroupItemPosition(position)) {
                return position;
            }
            position--;
        }
        return -1;
    }

    /**
     * 获取下一个分组标题view位置
     * @param firstVisiblePosition
     * @param visibleItemCount
     * @return
     */
    private int findNextGroupPosition(int firstVisiblePosition, int visibleItemCount) {
        for (int position = firstVisiblePosition + 1; position < firstVisiblePosition + visibleItemCount - 1; position++) {
            if (adapter.isGroup(position)) {
                return position;
            }
        }
        return -1;
    }

    /**
     * 是否是分组标题
     * @param position
     * @return
     */
    private boolean isGroupItemPosition(int position) {
        return adapter.isGroup(position);
    }

    private void changeTo(String navigation) {
        int position = ((SimpleGroupListAdapter) adapter).getTargetItemId(navigation);
        if (position >= 0) {
            setSelection(position);
        }
    }

    private void stopScroll(AbsListView view) {
        Field mFlingEndField = null;
        Method mFlingEndMethod = null;
        try {
            mFlingEndField = AbsListView.class.getDeclaredField("mFlingRunnable");
            mFlingEndMethod = Class.forName("android.widget.AbsListView$FlingRunnable").getDeclaredMethod("endFling");
            mFlingEndMethod.setAccessible(true);
            mFlingEndField.setAccessible(true);
            if (mFlingEndMethod != null) {
                mFlingEndMethod.invoke(mFlingEndField.get(view));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        this.adapter = (GroupListAdapter) adapter;
        navigations = ((SimpleGroupListAdapter) adapter).getNavigationItems();
        super.setAdapter(adapter);
    }

}
