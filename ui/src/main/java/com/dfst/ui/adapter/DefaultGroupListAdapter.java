package com.dfst.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by yanfei on 2016-11-07.
 */
public abstract class DefaultGroupListAdapter extends GroupListAdapter {

    public abstract int getGroupCount();

    public abstract int getChildCount(int groupPosition);

    public abstract Object getGroup(int groupPosition);

    public abstract Object getChild(int groupPosition, int childPosition);

    public abstract View getGroupView(int groupPosition, View convertView, ViewGroup parent);

    public abstract View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent);

    @Override
    public int getCount() {
        int count = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            count++;
            count += getChildCount(i);
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        int[] positions = exchange(position);
        if (positions[1] == -1)
            return getGroup(positions[0]);
        else
            return getChild(positions[0], positions[1]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int[] positions = exchange(position);
        int groupIndex = positions[0];
        int childIndex = positions[1];

        ViewHolder holder;
        if (convertView == null) {
            convertView = new FrameLayout(parent.getContext());
            holder = new ViewHolder();
            holder.groupLayout = new FrameLayout(parent.getContext());
            holder.childLayout = new FrameLayout(parent.getContext());
            ((FrameLayout) convertView).addView(holder.groupLayout);
            ((FrameLayout) convertView).addView(holder.childLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (positions[1] == -1) {
            if (holder.group == null) {
                holder.group = getGroupView(groupIndex, holder.group, parent);
                holder.groupLayout.addView(holder.group);
            } else {
                holder.group = getGroupView(groupIndex, holder.group, parent);
            }

            holder.groupLayout.setVisibility(View.VISIBLE);
            holder.childLayout.setVisibility(View.GONE);
        } else {
            if (holder.child == null) {
                holder.child = getChildView(groupIndex, childIndex, holder.child, parent);
                holder.childLayout.addView(holder.child);
            } else {
                holder.child = getChildView(groupIndex, childIndex, holder.child, parent);
            }
            holder.childLayout.setVisibility(View.VISIBLE);
            holder.groupLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isGroup(int position) {
        int[] positions = exchange(position);
        if (positions[1] >= 0)
            return false;
        else
            return true;
    }

    private int[] exchange(int position) {
        int[] groupChild = new int[2];
        int p = -1;
        for (int groupIndex = 0; groupIndex < getGroupCount(); groupIndex++) {
            p++;
            if (position == p)
                if (position == p) {
                    groupChild[0] = groupIndex;
                    groupChild[1] = -1;
                    return groupChild;
                }
            for(int childIndex = 0; childIndex < getChildCount(groupIndex); childIndex++) {
                p++;
                if (position == p) {
                    groupChild[0] = groupIndex;
                    groupChild[1] = childIndex;
                    return groupChild;
                }
            }
        }
        return groupChild;
    }

    private class ViewHolder {
        FrameLayout groupLayout;
        FrameLayout childLayout;
        View group;
        View child;
    }
}
