package com.dfst.easyandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.activity.CommonGroupListActivity.Date;
import com.dfst.easyandroid.activity.CommonGroupListActivity.Game;
import com.dfst.ui.adapter.DefaultGroupListAdapter;

/**
 * Created by yanfei on 2016-11-07.
 */
public class MyGroupListAdapter extends DefaultGroupListAdapter {

    private Context mContext;
    private List<Date> mData;

    public MyGroupListAdapter(Context context, List<Date> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).games.size();
    }

    @Override
    public Date getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Game getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).games.get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = View.inflate(mContext, R.layout.item_common_group_list_group, null);
            convertView.setBackgroundColor(Color.LTGRAY);
            holder.tv = (TextView) convertView.findViewById(R.id.group_list_group_date_textview);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        //convertView.setVisibility(View.VISIBLE);
        //if (groupPosition == 0) convertView.setVisibility(View.GONE);
        holder.tv.setText(mData.get(groupPosition).date);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = View.inflate(mContext, R.layout.item_common_group_list_child, null);
            holder.timeTv = (TextView) convertView.findViewById(R.id.group_list_game_time_textview);
            holder.gameTv = (TextView) convertView.findViewById(R.id.group_list_game_game_textview);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.timeTv.setText(mData.get(groupPosition).games.get(childPosition).time);
        holder.gameTv.setText(mData.get(groupPosition).games.get(childPosition).game);
        return convertView;
    }

    private class GroupHolder {
        TextView tv;
    }

    private class ChildHolder {
        TextView timeTv, gameTv;
    }
}
