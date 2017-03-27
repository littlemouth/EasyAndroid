package com.dfst.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by yanfei on 2016-06-27.
 */
public class SlideListView extends ListView {

    private Context mContext;
    private Transform mTransform;
    private boolean restore;
    private SlideAdapter mSlideAdapter;
    private ItemHelper mItemHelper;
    private List<?> mData;

    public SlideListView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mTransform = new Transform();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mTransform.item != null && mTransform.item.isSlide()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN
                    && mTransform.item.getRootViewScrollX() == mTransform.item.getMaxSlideX()) {
                if (ev.getY() < mTransform.item.getTop() || ev.getY() > mTransform.item.getBottom()) {
                    restore = true;
                    restore();
                    return true;
                } else {
                    MotionEvent newEv = MotionEvent.obtain(ev.getDownTime(), ev.getEventTime(),
                            ev.getAction(), ev.getX(), ev.getY() - mTransform.item.getTop(), ev.getPressure(), ev.getSize(),
                            ev.getMetaState(), ev.getXPrecision(), ev.getYPrecision(), ev.getDeviceId(),
                            ev.getEdgeFlags());
                    return mTransform.item.dispatchTouchEvent(newEv);
                }
            } else {
                return mTransform.item.dispatchTouchEvent(ev);
            }
        }
        if (restore == true) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                restore = false;
            }
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public void restore() {
        if (mTransform == null || mTransform.item == null) return;
        mTransform.item.setSlide(false);
        if (mTransform.item.getRootViewScrollX() != 0) {
            ObjectAnimator animator = ObjectAnimator.ofInt(mTransform.item.getRootView(), "scrollX", mTransform.item.getRootViewScrollX(), 0).setDuration(150);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mTransform.item = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }

    }

    public void initItems(List<?> data, ItemHelper itemHelper) {
        this.mData = data;
        mSlideAdapter = new SlideAdapter(mContext, data);
        this.mItemHelper = itemHelper;
        setAdapter(mSlideAdapter);
    }

    public void notifyDataSetChanged() {
        if (mSlideAdapter != null)
            mSlideAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        if (mSlideAdapter != null)
            mSlideAdapter.notifyDataSetInvalidated();
    }

    public void remove(int position) {
        if (mData != null)
            mData.remove(position);
        if (mSlideAdapter != null)
            mSlideAdapter.notifyDataSetChanged();
        restore();
    }

    public interface ItemHelper {
        View getView(int position, View convertView);

        View getOperationView(int position, View convertView);
    }

    public static abstract class SimpleItemHelper implements ItemHelper {

        public View getOperationView(int position, View convertView) {
            return null;
        }
    }

    public  class Transform {
        public SlideListItem item;
    }

    public class SlideAdapter extends BaseAdapter {
        private List<?> data;
        private Context aContext;
        public SlideAdapter(Context context, List<?> data) {
            this.data = data;
            aContext = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = new SlideListItem(aContext);
                ((SlideListItem) convertView).setTransform(mTransform);
                holder = new ViewHolder();
                holder.contentView = mItemHelper.getView(position, null);
                holder.operationView = mItemHelper.getOperationView(position, null);
                ((SlideListItem) convertView).setContentView(holder.contentView);
                if (holder.operationView != null) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    holder.operationView.setLayoutParams(params);
                    ((SlideListItem) convertView).setOperationView(holder.operationView);
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                mItemHelper.getView(position, holder.contentView);
                if (holder.operationView != null) {
                    mItemHelper.getOperationView(position, holder.operationView);
                }
            }
            ((SlideListItem) convertView).setPosition(position);
            ((SlideListItem) convertView).setOnDeleteListener(new SlideListItem.OnDeleteListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    mSlideAdapter.notifyDataSetChanged();
                    restore();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            View contentView, operationView;
        }

    }
}
