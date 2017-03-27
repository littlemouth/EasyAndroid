package com.dfst.easyandroid.activity;

import android.app.Activity;
import android.os.Bundle;

import com.dfst.easyandroid.R;
import com.dfst.easyandroid.adapter.ContractAdapter;
import com.dfst.ui.widget.GroupListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlemouth on 16-12-4
 */
public class ContractActivity extends Activity {
    private GroupListView groupListView;
    private ContractAdapter adapter;
    private List<ContractGroup> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        createData();
        groupListView = (GroupListView) findViewById(R.id.contract);
        adapter = new ContractAdapter(this, data);
        groupListView.setAdapter(adapter);
    }

    private void createData() {
        data = new ArrayList<ContractGroup>();
        ContractGroup g1 = new ContractGroup();
        g1.group = "A";
        List<String> items1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items1.add("item - " + i);
        }
        g1.items = items1;
        data.add(g1);

        ContractGroup g2 = new ContractGroup();
        g2.group = "B";
        List<String> items2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items2.add("item - " + i);
        }
        g2.items = items2;
        data.add(g2);

        ContractGroup g3 = new ContractGroup();
        g3.group = "C";
        List<String> items3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items3.add("item - " + i);
        }
        g3.items = items3;
        data.add(g3);

        ContractGroup g4 = new ContractGroup();
        g4.group = "D";
        List<String> items4 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items4.add("item - " + i);
        }
        g4.items = items4;
        data.add(g4);

        ContractGroup g5 = new ContractGroup();
        g5.group = "E";
        List<String> items5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items5.add("item - " + i);
        }
        g5.items = items5;
        data.add(g5);
    }


    public static class ContractGroup {
        public String group;
        public List<String> items;
    }
}
