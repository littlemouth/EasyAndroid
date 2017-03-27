package com.dfst.easyandroid.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.activity.FallViewActivity;

import java.util.List;

/**
 * Created by yanfei on 2016-12-20.
 */
public class FallViewAdapter extends BaseAdapter {

    private Context context;
    private List<FallViewActivity.FallItem> data;

    public FallViewAdapter(Context context, List<FallViewActivity.FallItem> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public FallViewActivity.FallItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fallview, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.fall_image);
            holder.desc = (TextView) convertView.findViewById(R.id.fall_desc);
            holder.desc.setTextSize(11);
            holder.desc.setGravity(Gravity.CENTER);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FallViewActivity.FallItem item = data.get(position);

        holder.image.setImageResource(item.resId);
        holder.desc.setText("第" + (position + 1) + "张图片");

        return convertView;
    }

    private class ViewHolder {
        TextView desc;
        ImageView image;
    }
}
