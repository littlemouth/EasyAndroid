package com.dfst.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfst.ui.R;

/**
 * Created by yanfei on 2016-06-27.
 */
public class SlideListItem extends LinearLayout {
    private Context mContext;
    private GestureDetector mGestureDetector;
    private SlideListView.Transform mTransform;
    private int mMaxScrollX;
    private boolean mIsSlide, isFling;
    private View mRoot;
    private FrameLayout mContentLayout;
    private LinearLayout mButtonLayout;
    private TextView mDeleteTv;
    private int position;

    private OnDeleteListener mOnDeleteListener;

    public SlideListItem(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.item_silde_list1, this);
        mRoot = findViewById(R.id.root_layout);
        mContentLayout = (FrameLayout) findViewById(R.id.item_content);
        mButtonLayout = (LinearLayout) findViewById(R.id.button_layout);
        mDeleteTv = (TextView) findViewById(R.id.delete_tv);
        if (mDeleteTv != null) {
            mDeleteTv.setOnTouchListener(new OnTouchListener() {
                private boolean down;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        down = true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP && down) {
                        if (mOnDeleteListener != null)
                            mOnDeleteListener.onClick(v);
                    }
                    return true;
                }
            });
        }
        mGestureDetector = new GestureDetector(mContext, new GestureListener());

        measureMaxScrollX();
    }

    private void measureMaxScrollX() {
        int widthMode = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightMode = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mButtonLayout.measure(widthMode, heightMode);
        int width = mButtonLayout.getMeasuredWidth();
        mMaxScrollX = width;
        LayoutParams params  = (LayoutParams) mRoot.getLayoutParams();
        params.rightMargin = -width;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isFling) {
                if (mRoot.getScrollX() == 0) {
                    mTransform.item = null;
                } else {
                    if(mRoot.getScrollX() > mMaxScrollX/2) {
                        ObjectAnimator.ofInt(mRoot, "scrollX", mRoot.getScrollX(), mMaxScrollX).setDuration(100).start();
                    } else if (mRoot.getScrollX() <= mMaxScrollX/2) {
                        ObjectAnimator.ofInt(mRoot, "scrollX", mRoot.getScrollX(), 0).setDuration(100).start();
                        mTransform.item = null;
                        mIsSlide = false;
                    }
                }
            } else {
                isFling = false;
            }

        }
        return result;
    }

    public boolean isSlide() {
        return mIsSlide;
    }

    public void setSlide(boolean slide) {
        this.mIsSlide = slide;
    }

    public int getMaxSlideX() {
        return mMaxScrollX;
    }

    public int getRootViewScrollX() {
        return mRoot.getScrollX();
    }

    public void setTransform(SlideListView.Transform transform) {
        mTransform = transform;
    }

    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
    }

    public void setOperationView(View view) {
        mButtonLayout.removeAllViews();
        mButtonLayout.addView(view);
        measureMaxScrollX();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public View getRootView() {
        return mRoot;
    }

    public View getContentView() {
        return mContentLayout.getChildAt(0);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.mOnDeleteListener = listener;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            mTransform.item = SlideListItem.this;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int scrollX = (int)distanceX;
            if (mRoot.getScrollX() + distanceX >= 0 && mRoot.getScrollX() + distanceX <= mMaxScrollX) {
                mRoot.scrollBy(scrollX, 0);
            } else {
                if (mRoot.getScrollX() + distanceX < 0) {
                    mRoot.scrollTo(0, 0);
                } else if (mRoot.getScrollX() + distanceX > mMaxScrollX) {
                    mRoot.scrollTo(mMaxScrollX, 0);
                }
            }
            mIsSlide = true;
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getX() - e1.getX() > 20) {
                ObjectAnimator.ofInt(mRoot, "scrollX", mRoot.getScrollX(), 0).setDuration(100).start();
                isFling = true;
                mTransform.item = null;
            } else if (e1.getX() - e2.getX() > 20) {
                ObjectAnimator.ofInt(mRoot, "scrollX", mRoot.getScrollX(), mMaxScrollX).setDuration(100).start();
                isFling = true;
            }
            return false;
        }
    }

    public interface OnDeleteListener {
        void onClick(View v);
    }

}
