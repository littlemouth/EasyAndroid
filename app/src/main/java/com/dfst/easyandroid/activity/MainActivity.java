package com.dfst.easyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.adapter.ListAdapter;
import com.dfst.easyandroid.entity.ListItem;
import com.dfst.media.SimpleRecorder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_activity_listview);
        listView.setAdapter(new ListAdapter(this, createData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(MainActivity.this, PageViewActivity.class);
                } else if (position == 1) {
                    intent = new Intent(MainActivity.this, TabViewActivity.class);
                } else if (position == 2) {
                    intent = new Intent(MainActivity.this, GroupListActivity.class);
                } else if (position == 3) {
                    intent = new Intent(MainActivity.this, SimpleRecorder.class);
                    startActivityForResult(intent, 10000);
                    return;
                } else if (position == 4) {
                    intent = new Intent(MainActivity.this, SlideListActivity.class);
                } else if (position == 5) {
                    intent = new Intent(MainActivity.this, FallViewActivity.class);
                }

                if (intent != null)
                    startActivity(intent);
            }
        });
    }

    private List<ListItem> createData() {
        List<ListItem> data = new ArrayList<>();

        // tab view
        ListItem pageViewItem = new ListItem("标签组件", "一个快速生成主页框架的组件");
        data.add(pageViewItem);

        // tab view
        ListItem tabViewItem = new ListItem("标签组件", "一个快速生成标签页的组件");
        data.add(tabViewItem);

        // group list
        ListItem groupListItem = new ListItem("分组列表组件", "一个快速生成分组列表的组件");
        data.add(groupListItem);

        // record
        ListItem recordListItem = new ListItem("录音组件", "一个通用录音机组件");
        data.add(recordListItem);

        // slide list
        ListItem slideListItem = new ListItem("滑动列表组件", "一个快速生成滑动列表的组件");
        data.add(slideListItem);

        // fall list
        ListItem fallListItem = new ListItem("瀑布流列表组件", "一个快速生成滑动列表的组件");
        data.add(fallListItem);

        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //String path = data.getData().getPath();
    }
}
