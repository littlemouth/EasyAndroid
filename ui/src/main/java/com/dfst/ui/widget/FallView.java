package com.dfst.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import android.widget.Toast;

import com.dfst.core.util.DensityUtil;
import com.dfst.ui.R;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yanfei on 2016-09-11.
 */
public class FallView extends AdapterView<ListAdapter> {
    private final int BOTTOM_BOTTOM = 0, TOP_BOTTOM = 1, TOP_TOP = 2, BOTTOM_TOP = 3;
    private Context mContext;
    private ListAdapter mAdapter;
    private Scroller mScroller;
    private GestureDetector mGesture;
    private Queue<View> mRemovedViewQueue = new LinkedList<>();
    private ColumnItem[] mColumnItems;
    private Stack<Integer> mRemovePositionStack = new Stack<>();
    private Rect mPadding = new Rect();
    private int mVerticalSpacing, mHorizontalSpacing;
    private int mMaxY = Integer.MAX_VALUE;
    private int mCurrentY, mNextY, mMaxDistanceY;
    private int mColumn, mDefaultColunm = 2;
    private int mItemWidth;
    private int mLastViewIndex = 0;
    private int mScrollBarWidth, mScrollBarMarginRight;
    private int mScrollBarTop, mScrollBarBottom;

    private Paint mPaint;


    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mMaxY = Integer.MAX_VALUE;
            invalidate();
            requestLayout();
        }

        @Override
        public void onInvalidated() {
        }
    };

    public FallView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.FallView);
        mColumn = typedArray.getInt(R.styleable.FallView_fallview_column, 2);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mGesture = new GestureDetector(getContext(), mOnGesture);

        mHorizontalSpacing = DensityUtil.dip2px(mContext, 3);
        mVerticalSpacing = DensityUtil.dip2px(mContext, 3);

        mCurrentY = 0;
        mNextY = 0;

        mColumnItems = new ColumnItem[mColumn];
        for (int index = 0; index < mColumn; index++) {
            mColumnItems[index] = new ColumnItem(index);
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.LTGRAY);

        mScrollBarMarginRight = DensityUtil.dip2px(mContext, 1);
        mScrollBarWidth = DensityUtil.dip2px(mContext, 5);

    }

    @Override
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(mDataSetObserver);
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPadding.top = getPaddingTop();
        mPadding.bottom = getPaddingBottom();
        mPadding.left = getPaddingLeft();
        mPadding.right = getPaddingRight();
        mItemWidth = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - (mColumn - 1) * mHorizontalSpacing) / mColumn;
        mMaxDistanceY = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutChildren();
    }

    private void layoutChildren() {
        if (mAdapter == null) {
            return;
        }

        if (mScroller.computeScrollOffset()) {
            mNextY = mScroller.getCurrY();
        }

        if (mNextY >= mMaxY) {
            mNextY = mMaxY;
            mScroller.forceFinished(true);
        }

        if (mNextY <= 0) {
            mNextY = 0;
            mScroller.forceFinished(true);
        }

        int distanceY = mCurrentY - mNextY;

        while (distanceY >= mMaxDistanceY) {
            int stepDistanceY = mMaxDistanceY * 9 / 10;
            removeInvisibleItems(stepDistanceY);
            fillList(stepDistanceY);
            layoutItems(stepDistanceY);
            distanceY -= stepDistanceY;
        }

        while (distanceY <= -mMaxDistanceY) {
            int stepDistanceY = mMaxDistanceY * 9 / 10;
            removeInvisibleItems(-stepDistanceY);
            fillList(-stepDistanceY);
            layoutItems(-stepDistanceY);
            distanceY += stepDistanceY;
        }

        removeInvisibleItems(distanceY);
        fillList(distanceY);
        layoutItems(distanceY);
        mCurrentY = mNextY;

        if (!mScroller.isFinished()) {
            post(new Runnable() {
                @Override
                public void run() {
                    requestLayout();
                }
            });
        }
    }

    private void fillList(int distanceY) {
        int minBottomColumn = targetItemColumn(BOTTOM_BOTTOM);
        fillListBottom(mColumnItems[minBottomColumn], distanceY);

        int maxTopColumn = targetItemColumn(TOP_TOP);
        fillListTop(mColumnItems[maxTopColumn], distanceY);
    }

    private void fillListBottom(ColumnItem columnItem, int distanceY) {
        int column = columnItem.column;
        int lastBottom = 0;
        if (columnItem.items.size() > 0) {
            lastBottom = columnItem.items.getLast().view.getBottom();
        }
        if (distanceY <= 0 && lastBottom + mVerticalSpacing + distanceY < getHeight() - mPadding.bottom
                && mLastViewIndex < mAdapter.getCount()) {
            View child = mAdapter.getView(mLastViewIndex, mRemovedViewQueue.poll(), this);
            addAndMeasureChild(child, -1);

            int left = mPadding.left + (mItemWidth + mHorizontalSpacing) * column;
            int right = left + mItemWidth;
            int top = lastBottom + (mLastViewIndex / mColumn > 0 ? mVerticalSpacing : mPadding.top);
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);

            columnItem.maxY = mCurrentY + child.getBottom() - getHeight() + mPadding.bottom;

            if (mLastViewIndex == mAdapter.getCount() - 1) {
                //mMaxY = mCurrentY + child.getBottom() - getHeight() + mPadding.bottom;
                mMaxY = mColumnItems[0].maxY;
                for (ColumnItem item : mColumnItems) {
                    if (item.maxY > mMaxY) {
                        mMaxY = item.maxY;
                    }
                }
            }

            columnItem.items.addLast(new Item(mLastViewIndex, child));
            mLastViewIndex++;

            int targetColumn = targetItemColumn(BOTTOM_BOTTOM);
            fillListBottom(mColumnItems[targetColumn], distanceY);
        }
    }

    private void fillListTop(ColumnItem columnItem, int distanceY) {
        int column = columnItem.column;
        int firstTop = 0;
        if (columnItem.items.size() > 0) {
            firstTop = columnItem.items.getFirst().view.getTop();
        }
        if (distanceY > 0 && firstTop + mVerticalSpacing + distanceY > mPadding.top && mRemovePositionStack.size() > 0
                && columnItem.items.size() > 0) {
            int position = mRemovePositionStack.pop();
            View firstView = columnItem.items.getFirst().view;
            View child = mAdapter.getView(position, mRemovedViewQueue.poll(), this);
            addAndMeasureChild(child, 0);
            int left = mPadding.left + (mItemWidth + mHorizontalSpacing) * column;
            int right = left + mItemWidth;
            int bottom = firstView.getTop() - mVerticalSpacing;
            int top = bottom - child.getMeasuredHeight();
            child.layout(left, top, right, bottom);

            columnItem.items.addFirst(new Item(position, child));

            int targetColumn = targetItemColumn(TOP_TOP);
            fillListTop(mColumnItems[targetColumn], distanceY);
        }
    }

    private void removeInvisibleItems(int distanceY) {
        int targetColumn = targetItemColumn(TOP_BOTTOM);
        removeTop(mColumnItems[targetColumn], distanceY);

        targetColumn = targetItemColumn(BOTTOM_TOP);
        removeBottom(mColumnItems[targetColumn], distanceY);
    }


    private void layoutItems(int distanceY) {
        if (getChildCount() > 0) {
            for (int index = 0; index < getChildCount(); index++) {
                View child = getChildAt(index);
                child.layout(child.getLeft(), child.getTop() + distanceY,
                        child.getRight(), child.getBottom() + distanceY);
            }
        }
    }

    private void addAndMeasureChild(View child, int index) {
        if (child.getBackground() == null) {
            child.setBackgroundColor(Color.WHITE);
        }

        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(mItemWidth, LayoutParams.MATCH_PARENT);
        }

        addViewInLayout(child, index, params, false);

        child.measure(
                MeasureSpec.makeMeasureSpec(mItemWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
    }

    private void removeTop(ColumnItem columnItem, int distanceY) {
        if (columnItem.items.size() > 0) {
            View child = columnItem.items.getFirst().view;
            int bottom = child.getBottom();
            if (distanceY < 0 && bottom + distanceY < mPadding.top) {
                mRemovePositionStack.push(columnItem.items.getFirst().position);
                columnItem.items.removeFirst();
                mRemovedViewQueue.offer(child);
                removeViewInLayout(child);

                int targetColumn = targetItemColumn(TOP_BOTTOM);
                removeTop(mColumnItems[targetColumn], distanceY);
            }
        }
    }

    private void removeBottom(ColumnItem columnItem, int distanceY) {
        if (columnItem.items.size() > 0) {
            View child = columnItem.items.getLast().view;
            int top = child.getTop();
            if (distanceY > 0 && top + distanceY > getHeight() - mPadding.bottom) {
                columnItem.items.removeLast();
                mRemovedViewQueue.offer(child);
                removeViewInLayout(child);
                mLastViewIndex--;

                int targetColumn = targetItemColumn(BOTTOM_TOP);
                removeBottom(mColumnItems[targetColumn], distanceY);
            }
        }
    }

    private int targetItemColumn(int type) {
        int result = 0;
        if (type == BOTTOM_BOTTOM || type == TOP_BOTTOM) {
            int minBottom = 0;
            for (ColumnItem columnItem : mColumnItems) {
                if (columnItem.items.size() == 0) return columnItem.column;
                View view = type == BOTTOM_BOTTOM ? columnItem.items.getLast().view : columnItem.items.getFirst().view;
                if (columnItem.column == 0) {
                    minBottom = view.getBottom();
                } else {
                    if (view.getBottom() < minBottom) {
                        result = columnItem.column;
                        minBottom = view.getBottom();
                    }
                }
            }
        } else if (type == BOTTOM_TOP || type == TOP_TOP) {
            int maxTop = 0;
            for (int index = mColumn - 1; index >= 0; index--) {
                ColumnItem columnItem = mColumnItems[index];
                if (columnItem.items.size() == 0) return columnItem.column;
                View view = type == BOTTOM_TOP ? columnItem.items.getLast().view : columnItem.items.getFirst().view;
                if (index == mColumn - 1) {
                    result = index;
                    maxTop = view.getTop();
                } else {
                    if (view.getTop() > maxTop) {
                        result = columnItem.column;
                        maxTop = view.getTop();
                    }
                }
            }
        }
        return result;
    }

    private void refreshScrollBar() {
        float start = mRemovePositionStack.size();
        float end = mLastViewIndex + 1;
        for (ColumnItem columnItem : mColumnItems) {
            if (columnItem.items.size() > 0) {
                View firstView = columnItem.items.getFirst().view;
                View lastView = columnItem.items.getLast().view;
                start += 1.0f * Math.abs(firstView.getTop() - mPadding.top) / firstView.getMeasuredHeight();
                end -= 1.0f * Math.abs(lastView.getBottom() - getHeight() + mPadding.bottom) / lastView.getMeasuredHeight();
            }
        }
        int total = mAdapter.getCount();
        mScrollBarTop = (int) (start / total * getHeight());
        mScrollBarBottom = (int) (end / total * getHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        handled |= mGesture.onTouchEvent(ev);
        return handled;
    }

    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            mScroller.forceFinished(true);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            synchronized (FallView.this) {
                if (velocityY > 5000) {
                    velocityY = 5000;
                } else  if (velocityY < -5000) {
                    velocityY = -5000;
                }
                mScroller.fling(0, mNextY, 0, (int) -velocityY, 0, 0, 0, mMaxY);
                Log.i("Test", "================ " + velocityY);
            }
            requestLayout();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            synchronized (FallView.this) {
                mNextY += (int) distanceY;
            }
            requestLayout();
            //refreshScrollBar();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            for (int index = 0; index < getChildCount(); index++) {
                View child = getChildAt(index);
                if (isEventWithinView(e, child)) {
                    outer : for(ColumnItem columnItem : mColumnItems) {
                        for (Item item : columnItem.items) {
                            if (item.view == child) {
                                Toast.makeText(mContext, "第 " + (item.position + 1) + " 张图片", Toast.LENGTH_SHORT).show();
                                break outer;
                            }
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        private boolean isEventWithinView(MotionEvent e, View child) {
            Rect viewRect = new Rect();
            int[] childPosition = new int[2];
            child.getLocationOnScreen(childPosition);
            int left = childPosition[0];
            int right = left + child.getWidth();
            int top = childPosition[1];
            int bottom = top + child.getHeight();
            viewRect.set(left, top, right, bottom);
            return viewRect.contains((int) e.getRawX(), (int) e.getRawY());
        }
    };

    private class ColumnItem {
        int column;
        int maxY;
        LinkedList<Item> items;

        public ColumnItem(int column) {
            this.column = column;
            items = new LinkedList<>();
        }
    }

    private class Item {
        int position;
        View view;

        public Item(int position, View view) {
            this.position = position;
            this.view = view;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPaint.setColor(Color.LTGRAY);
        canvas.drawRect(mPadding.left, mPadding.top,
                getWidth() - mPadding.right, getHeight() - mPadding.bottom, mPaint);
        super.dispatchDraw(canvas);
        refreshScrollBar();

        mPaint.setColor(Color.argb(100, 100, 100, 100));
        canvas.drawRect(getWidth() - mScrollBarMarginRight - mScrollBarWidth, mScrollBarTop,
                getWidth() - mScrollBarMarginRight, mScrollBarBottom, mPaint);
    }
}
