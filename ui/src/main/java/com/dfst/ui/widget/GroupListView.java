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
public class GroupListView extends ListView {
    private Context mContext;
    private GroupListAdapter adapter;
    private View topGroupView;
    private int translateY;

    private Paint paint;

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

    public GroupListView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawTopGroupView(canvas);
        //drawNavigationBar(canvas);
        //drawNavigationCenterText(canvas);
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

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        setOnScrollListener(mScrollListener);
    }

    /**
     * 生成置顶组标题view
     * @param currentGroupPosition
     * @param firstVisiblePosition
     * @param visibleItemCount
     */
    private void createTopGroupView(int currentGroupPosition, int firstVisiblePosition, int visibleItemCount) {
        topGroupView = adapter.getView(currentGroupPosition, null, GroupListView.this);

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
        super.setAdapter(adapter);
    }

}
