package com.dfst.easyandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.entity.ListItem;

/**
 * Created by yanfei on 2016-10-26.
 */
public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<ListItem> data;

    public ListAdapter(Context context, List<ListItem> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ListItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_main_list_item, null);
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.main_activity_listview_item_title_textview);
            holder.descriptionTv = (TextView) convertView.findViewById(R.id.main_activity_listview_item_description_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem item = data.get(position);
        holder.titleTv.setText(item.title);
        holder.descriptionTv.setText(item.dectription);

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTv;
        public TextView descriptionTv;
    }
}
