package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.adapter.ListAdapter;
import com.dfst.easyandroid.entity.ListItem;

/**
 * Created by yanfei on 2016-11-07.
 */
public class GroupListActivity extends Activity {
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
                    intent = new Intent(GroupListActivity.this, SimpleGroupListActivity.class);
                } else if (position == 1) {
                    intent = new Intent(GroupListActivity.this, CommonGroupListActivity.class);
                } else if (position == 2) {
                    intent = new Intent(GroupListActivity.this, ContractActivity.class);
                }

                if (intent != null)
                    startActivity(intent);
            }
        });
    }

    private List<ListItem> createData() {
        List<ListItem> data = new ArrayList<>();

        // tab view
        ListItem tabViewItem = new ListItem("简单分组列表", "快速生成联系人列表");
        data.add(tabViewItem);

        // group list
        ListItem groupListItem = new ListItem("常规分组列表组件", "构建常规分组列表");
        data.add(groupListItem);

        // group list
        ListItem contractItem = new ListItem("通讯录", "构建通讯录");
        data.add(contractItem);

        return data;
    }
}
