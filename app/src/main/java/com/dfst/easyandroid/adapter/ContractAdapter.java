package com.dfst.easyandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dfst.easyandroid.activity.ContractActivity.ContractGroup;
import com.dfst.ui.adapter.DefaultGroupListAdapter;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by littlemouth on 16-12-4.
 */
public class ContractAdapter extends DefaultGroupListAdapter {

    private List<ContractGroup> data;
    private Context context;

    public ContractAdapter(Context context, List<ContractGroup> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return data.get(groupPosition).items.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).items.get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(context);
            convertView.setPadding(0, 30, 0, 30);
            convertView.setBackgroundColor(Color.GRAY);
        }
        ((TextView) convertView).setText(data.get(groupPosition).group);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(context);
            convertView.setPadding(0, 30, 0, 30);
        }
        ((TextView) convertView).setText(data.get(groupPosition).items.get(childPosition));
        return convertView;
    }
}
