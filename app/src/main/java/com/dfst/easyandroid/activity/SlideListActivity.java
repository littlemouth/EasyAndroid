package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfst.easyandroid.R;
import com.dfst.ui.widget.SlideListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfei on 2017-03-03.
 */
public class SlideListActivity extends Activity {

    private SlideListView slideListView;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_list);

        slideListView = (SlideListView) findViewById(R.id.slide_lv);

        getData();
        slideListView.initItems(data, new SlideListView.SimpleItemHelper() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = View.inflate(SlideListActivity.this, R.layout.item_silde_convertview, null);
                }
                ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
                TextView textView = (TextView) convertView.findViewById(R.id.contentTv);
                icon.setImageResource(R.mipmap.ic_launcher);
                textView.setText(data.get(position));
                return convertView;
            }

            /*@Override
            public View getOperationView(final int position, View convertView) {
                if (convertView == null) {
                    convertView = View.inflate(SlideListActivity.this, R.layout.item_operation, null);
                }
                TextView editTv = (TextView) convertView.findViewById(R.id.editTv);
                editTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SlideListActivity.this, "edit", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView deleteTv = (TextView) convertView.findViewById(R.id.deleteTv);
                deleteTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SlideListActivity.this, "delete", Toast.LENGTH_SHORT).show();
                        slideListView.remove(position);
                    }
                });
                *//*convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.remove(position);
                        slideListView.restore();
                    }
                });*//*
                return convertView;
            }*/

        });
    }

    private void getData() {
        for (int i = 1; i <= 20; i++) {
            data.add("item - " + i);
        }
    }
}
